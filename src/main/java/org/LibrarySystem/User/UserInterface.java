package org.LibrarySystem.User;

import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JPanel implements ActionListener {
    private JButton back;
    private JLabel label;
    private static JLabel label_user;
    private JButton btn_return;
    private JButton btn_borrow;
    private JButton btn_prolong;
    private JButton btn_personal;
    //user_UI
    public UserInterface() {
        setBackground(new Color(0, 191, 255));
        setLayout(null);

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 26));
        back.setBounds(0, 0, 115, 82);
        add(back);
        back.addActionListener(this);

        label = new JLabel("用户界面");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(512, 47, 294, 105);
        add(label);

        label_user = new JLabel("New label");
        label_user.setHorizontalAlignment(SwingConstants.CENTER);
        label_user.setFont(new Font("宋体", Font.BOLD, 28));
        label_user.setBounds(944, 0, 356, 56);
        add(label_user);

        btn_borrow = new JButton("借书");
        btn_borrow.setFont(new Font("宋体", Font.BOLD, 40));
        btn_borrow.setBounds(31, 233, 280, 505);
        add(btn_borrow);
        btn_borrow.addActionListener(this);

        btn_return = new JButton("还书");
        btn_return.setFont(new Font("宋体", Font.BOLD, 40));
        btn_return.setBounds(351, 233, 280, 505);
        add(btn_return);
        btn_return.addActionListener(this);

        btn_prolong = new JButton("续借");
        btn_prolong.setFont(new Font("宋体", Font.BOLD, 40));
        btn_prolong.setBounds(674, 233, 280, 505);
        add(btn_prolong);
        btn_prolong.addActionListener(this);

        btn_personal = new JButton("个人信息");
        btn_personal.setFont(new Font("宋体", Font.BOLD, 40));
        btn_personal.setBounds(999, 233, 280, 505);
        add(btn_personal);
        btn_personal.addActionListener(this);

    }
    public static void setUser(){
        label_user.setText("当前用户: "+ Basic_Information.user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back){
            MainInterface.User_to_Main();
        }else if(e.getSource()==btn_return){
            MainInterface.User_to_Return();
        } else if (e.getSource()==btn_borrow) {
            MainInterface.User_to_Borrow();
        } else if (e.getSource()==btn_personal) {
            MainInterface.User_to_Personal();
        }else if(e.getSource()==btn_prolong){
            MainInterface.User_to_ProlongBook();
        }
    }

}
