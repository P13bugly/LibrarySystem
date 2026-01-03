package org.LibrarySystem.Manager;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class State_search extends JPanel implements ActionListener {
    private JLabel label;
    private JButton back;
    private JTextField tf_className;
    private JLabel label_className;
    private JButton btn_search;

    public State_search() {
        setBackground(new Color(250, 250, 210));
        setLayout(null);

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 26));
        back.setBounds(0, 0, 115, 82);
        add(back);
        back.addActionListener(this);

        label = new JLabel("图书库状态查询");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(530, 20, 294, 105);
        add(label);

        // 标签文字微调，提示用户可以不填
        label_className = new JLabel("类别名称(空查所有)：");
        label_className.setFont(new Font("宋体", Font.BOLD, 35));
        label_className.setBounds(141, 330, 383, 55); // 调整坐标适应文字
        add(label_className);

        tf_className = new JTextField();
        tf_className.setFont(new Font("宋体", Font.BOLD, 35));
        tf_className.setColumns(10);
        tf_className.setBounds(542, 320, 486, 75);
        add(tf_className);

        btn_search = new JButton("查询");
        btn_search.setFont(new Font("宋体", Font.BOLD, 35));
        btn_search.setBounds(558, 567, 251, 80);
        add(btn_search);
        btn_search.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back) {
            MainInterface.State_to_Manager();
        } else if(e.getSource() == btn_search) {

            String className = tf_className.getText().trim();
            Basic_Information.search_className = className;


            sqlConn.search_className(className);

            if (!Basic_Information.bookArray.isEmpty()) {
                JOptionPane.showConfirmDialog(null, "查询成功，共找到 " + Basic_Information.bookArray.size() + " 本书", "提示", JOptionPane.OK_CANCEL_OPTION);
                MainInterface.State_to_StateInfo();
                State_Output.setTextTable();
                tf_className.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "未找到该类别的图书或书库为空", "查询无结果", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}