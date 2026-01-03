package org.LibrarySystem.User;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProlongBook extends JPanel implements ActionListener {
    private JButton back;
    private JTextField tf_className;
    private JTextField tf_number;
    private JTextField tf_dateOff;
    private JLabel lb_number;
    private JLabel lb_className;
    private JLabel lb_dateOff;
    private JButton btn_prolong;

    public ProlongBook() {
        setBackground(new Color(250, 240, 230));
        setLayout(null);

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 26));
        back.setBounds(0, 0, 115, 82);
        add(back);
        back.addActionListener(this);

        JLabel label = new JLabel("续借系统");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(530, 20, 294, 105);
        add(label);

        lb_className = new JLabel("书库类别名称(选填)：");
        lb_className.setFont(new Font("宋体", Font.BOLD, 35));
        lb_className.setBounds(181, 216, 383, 55);
        add(lb_className);

        tf_className = new JTextField();
        tf_className.setFont(new Font("宋体", Font.BOLD, 35));
        tf_className.setColumns(10);
        tf_className.setBounds(564, 206, 516, 75);
        add(tf_className);

        lb_number = new JLabel("书籍编号：");
        lb_number.setFont(new Font("宋体", Font.BOLD, 35));
        lb_number.setBounds(281, 360, 283, 55);
        add(lb_number);

        lb_dateOff = new JLabel("续借期限：");
        lb_dateOff.setFont(new Font("宋体", Font.BOLD, 35));
        lb_dateOff.setBounds(281, 498, 283, 55);
        add(lb_dateOff);

        tf_number = new JTextField();
        tf_number.setFont(new Font("宋体", Font.BOLD, 35));
        tf_number.setColumns(10);
        tf_number.setBounds(564, 350, 516, 75);
        add(tf_number);

        tf_dateOff = new JTextField();
        tf_dateOff.setFont(new Font("宋体", Font.BOLD, 35));
        tf_dateOff.setColumns(10);
        tf_dateOff.setBounds(564, 488, 516, 75);
        add(tf_dateOff);

        btn_prolong = new JButton("续借");
        btn_prolong.setFont(new Font("宋体", Font.BOLD, 35));
        btn_prolong.setBounds(554, 661, 251, 80);
        add(btn_prolong);
        btn_prolong.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back){
            MainInterface.ProlongBook_to_User();
        } else if (e.getSource()==btn_prolong) {
            String number = tf_number.getText().trim();
            String dateOff = tf_dateOff.getText().trim();

            // 简单校验
            if(number.isEmpty() || dateOff.isEmpty()){
                JOptionPane.showMessageDialog(null,"请填写编号和日期");
                return;
            }

            sqlConn.prolongBook_Update(number, dateOff);

            JOptionPane.showMessageDialog(null, "续借成功！");
            tf_className.setText("");
            tf_dateOff.setText("");
            tf_number.setText("");
            MainInterface.ProlongBook_to_User();
        }
    }
}