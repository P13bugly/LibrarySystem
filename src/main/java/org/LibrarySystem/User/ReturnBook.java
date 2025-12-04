package org.LibrarySystem.User;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReturnBook extends JPanel implements ActionListener {
    private JButton back;
    private JTextField tf_className;
    private JTextField tf_number;
    private JButton btn_return;

    public ReturnBook() {
        setBackground(new Color(250, 250, 210));
        setLayout(new BorderLayout(0, 0));

        //top menu
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); // 透明
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 20));
        back.setPreferredSize(new Dimension(100, 40));
        back.addActionListener(this);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(back);
        leftPanel.setPreferredSize(new Dimension(150, 50));

        JLabel titleLabel = new JLabel("还书系统", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 40));

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(150, 50));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        //middle menu
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10); // 组件之间的间距
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //first row
        JLabel lb_className = new JLabel("书库类别名称：");
        lb_className.setFont(new Font("微软雅黑", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(lb_className, gbc);

        tf_className = new JTextField();
        tf_className.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        tf_className.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1; gbc.gridy = 0;
        centerPanel.add(tf_className, gbc);

        //second row
        JLabel lb_number = new JLabel("图书编号：");
        lb_number.setFont(new Font("微软雅黑", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 1;
        centerPanel.add(lb_number, gbc);

        tf_number = new JTextField();
        tf_number.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        tf_number.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1; gbc.gridy = 1;
        centerPanel.add(tf_number, gbc);

        add(centerPanel, BorderLayout.CENTER);
        //bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0)); // 距离底部留些空间

        btn_return = new JButton("归还");
        btn_return.setFont(new Font("宋体", Font.BOLD, 30));
        btn_return.setPreferredSize(new Dimension(200, 60));
        btn_return.setBackground(new Color(255, 165, 0)); // 橙色按钮，显眼
        btn_return.setForeground(Color.WHITE);
        btn_return.addActionListener(this);

        bottomPanel.add(btn_return);
        add(bottomPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back){
            MainInterface.Return_to_User();
        } else if (e.getSource()==btn_return) {
            String className = tf_className.getText().trim();
            String number = tf_number.getText().trim();
            //自动获取当前日期
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String today = now.format(formatter);

            System.out.println("当前系统日期(自动获取): " + today);


            try {

                int deadline = sqlConn.search_bookDateOff(className, number);
                int returnDate = Integer.parseInt(today);

                if (deadline < returnDate) {
                    JOptionPane.showMessageDialog(null, "已超期！请前往缴纳罚款。", "逾期提醒", JOptionPane.WARNING_MESSAGE);

                }

                sqlConn.returnBook_BookUpdate(className, number, Basic_Information.user, today);
                sqlConn.returnBook_UserUpdate(number, Basic_Information.user);

                JOptionPane.showMessageDialog(null, "还书成功！");

                // 清空并跳转
                tf_className.setText("");
                tf_number.setText("");
                MainInterface.Return_to_User();
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
