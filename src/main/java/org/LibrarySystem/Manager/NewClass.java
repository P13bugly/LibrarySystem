package org.LibrarySystem.Manager;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewClass extends JPanel implements ActionListener {
    private JLabel label;
    private JTextField tf_classNumber;
    private JTextField tf_className;
    private JLabel lb_classNumber;
    private JLabel lb_className;
    private JButton btn_submit;
    private JButton back;

    public NewClass() {
        setBackground(new Color(175, 238, 238));
        setLayout(null);

        label = new JLabel("新建图书库");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(542, 20, 294, 105);
        add(label);

        lb_classNumber = new JLabel("分类编号：");
        lb_classNumber.setFont(new Font("宋体", Font.BOLD, 35));
        lb_classNumber.setBounds(361, 260, 194, 55);
        add(lb_classNumber);

        tf_classNumber = new JTextField();
        tf_classNumber.setFont(new Font("宋体", Font.BOLD, 35));
        tf_classNumber.setColumns(10);
        tf_classNumber.setBounds(555, 250, 399, 75);
        add(tf_classNumber);

        tf_className = new JTextField();
        tf_className.setFont(new Font("宋体", Font.BOLD, 35));
        tf_className.setColumns(10);
        tf_className.setBounds(555, 431, 399, 75);
        add(tf_className);

        lb_className = new JLabel("分类名称：");
        lb_className.setFont(new Font("宋体", Font.BOLD, 35));
        lb_className.setBounds(361, 441, 194, 55);
        add(lb_className);

        btn_submit = new JButton("提交");
        btn_submit.setFont(new Font("宋体", Font.BOLD, 35));
        btn_submit.setBounds(585, 637, 251, 80);
        add(btn_submit);
        btn_submit.addActionListener(this);

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 26));
        back.setBounds(0, 0, 115, 82);
        add(back);
        back.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back) {
            MainInterface.NewClass_to_Manager();
        }else if(e.getSource() == btn_submit){
            if(!sqlConn.is_Table(tf_className.getText().toLowerCase()+"book")) {
                sqlConn.newClass(tf_classNumber.getText(), tf_className.getText());
                tf_classNumber.setText("");
                tf_className.setText("");
                JOptionPane.showConfirmDialog(null, "恭喜", "添加新类别成功", JOptionPane.OK_CANCEL_OPTION);
            }else {
                JOptionPane.showMessageDialog(null, "该类别已存在", "添加新类别失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
