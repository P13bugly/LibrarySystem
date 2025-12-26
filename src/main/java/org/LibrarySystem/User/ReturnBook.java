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
        topPanel.setOpaque(false);
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
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //first row
        // 虽然数据库不需要 className 了，但为了保持界面结构，暂时保留输入框，但不参与逻辑
        JLabel lb_className = new JLabel("书库类别名称(选填)：");
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
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));

        btn_return = new JButton("归还");
        btn_return.setFont(new Font("宋体", Font.BOLD, 30));
        btn_return.setPreferredSize(new Dimension(200, 60));
        btn_return.setBackground(new Color(255, 165, 0));
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
            String number = tf_number.getText().trim();

            // 简单校验
            if(number.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入图书编号！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // 检查书是否真的是借出状态
                String state = sqlConn.search_bookState(number);
                if ("out".equals(state)) {
                    sqlConn.returnBook_Update(number);
                    JOptionPane.showMessageDialog(null, "还书成功！");
                    tf_className.setText("");
                    tf_number.setText("");
                    MainInterface.Return_to_User();
                } else if ("null".equals(state)) {
                    JOptionPane.showMessageDialog(null, "书籍编号不存在！", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "该书并未借出，无需归还。", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "系统错误: " + ex.getMessage());
            }
        }
    }
}