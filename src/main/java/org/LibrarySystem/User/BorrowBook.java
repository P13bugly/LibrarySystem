package org.LibrarySystem.User;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowBook extends JPanel implements ActionListener {
    private JLabel label;
    private JButton back;
    private JTextField tf_className;
    private JLabel label_className;
    private JButton btn_search;

    public BorrowBook(){
        setBackground(new Color(250, 250, 210));
        setLayout(null);

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 26));
        back.setBounds(0, 0, 115, 82);
        add(back);
        back.addActionListener(this);

        label = new JLabel("借书系统");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(530, 62, 294, 105);
        add(label);

        label_className = new JLabel("书库类别名称：");
        label_className.setFont(new Font("宋体", Font.BOLD, 35));
        label_className.setBounds(241, 330, 283, 55);
        add(label_className);

        tf_className = new JTextField();
        tf_className.setFont(new Font("宋体", Font.BOLD, 35));
        tf_className.setColumns(10);
        tf_className.setBounds(502, 320, 526, 75);
        add(tf_className);

        btn_search = new JButton("查询");
        btn_search.setFont(new Font("宋体", Font.BOLD, 35));
        btn_search.setBounds(558, 567, 251, 80);
        add(btn_search);
        btn_search.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==back){
            MainInterface.Borrow_to_User();
        } else if (e.getSource()==btn_search) {
            String className= tf_className.getText();
            Basic_Information.search_className=className;
            //search
            if(sqlConn.is_Table(className)){
                sqlConn.search_className(className);
                MainInterface.Borrow_to_BorrowInfo();
                BorrowBook_Output.setTable();
                tf_className.setText("");
            }else {
                JOptionPane.showMessageDialog(null, "不存在该类别", "查询失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
