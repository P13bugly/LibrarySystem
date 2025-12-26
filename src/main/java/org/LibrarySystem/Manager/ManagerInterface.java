package org.LibrarySystem.Manager;

import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerInterface extends JPanel implements ActionListener {
    private JLabel label;
    private JButton back;
    private JButton btn_insertBook;
    private JButton btn_state;
    private JButton btn_updateBook;
    private static JLabel label_Manager;

    public ManagerInterface(){
        setBackground(Color.CYAN);
        setLayout(null);

        label = new JLabel("管理员界面");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(530, 20, 294, 105);
        add(label);

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 26));
        back.setBounds(0, 0, 115, 82);
        add(back);
        back.addActionListener(this);

        btn_insertBook = new JButton("录入新书");
        btn_insertBook.setFont(new Font("宋体", Font.BOLD, 40));
        btn_insertBook.setBounds(115, 241, 280, 505);
        add(btn_insertBook);
        btn_insertBook.addActionListener(this);

        btn_updateBook = new JButton("更新信息");
        btn_updateBook.setFont(new Font("宋体", Font.BOLD, 40));
        btn_updateBook.setBounds(510, 241, 280, 505);
        add(btn_updateBook);
        btn_updateBook.addActionListener(this);

        btn_state = new JButton("馆藏状态");
        btn_state.setFont(new Font("宋体", Font.BOLD, 40));
        btn_state.setBounds(905, 241, 280, 505);
        add(btn_state);
        btn_state.addActionListener(this);

        label_Manager = new JLabel("New label");
        label_Manager.setHorizontalAlignment(SwingConstants.CENTER);
        label_Manager.setFont(new Font("宋体", Font.BOLD, 28));
        label_Manager.setBounds(954, 0, 348, 56);
        add(label_Manager);
    }

    public static void setManger(){
        label_Manager.setText("当前管理员:"+ Basic_Information.manager);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            MainInterface.Manager_to_Main();
        } else if (e.getSource() == btn_insertBook) {
            MainInterface.Manager_to_Insert();
        } else if (e.getSource() == btn_state) {
            MainInterface.Manager_to_State();
        } else if (e.getSource() == btn_updateBook) {
            MainInterface.Manager_to_Update();
        }
    }
}



