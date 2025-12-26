package org.LibrarySystem.Manager;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertBook extends JPanel implements ActionListener {
    // ... (UI组件定义保持不变) ...
    private JTextField tf_number;
    private JTextField tf_classNumber;
    private JTextField tf_name;
    private JTextField tf_className;
    private JTextField tf_price;
    private JTextField tf_state;
    private JTextField tf_total;
    private JLabel lb_number, lb_classNumber, lb_name, lb_className, lb_price, lb_state, lb_total;
    private JButton back, btn_submit;

    public InsertBook(){
        // ... (UI布局代码保持不变，请直接复制之前的布局代码) ...
        setBackground(Color.PINK);
        setLayout(null);

        JLabel label = new JLabel("新书录入");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(530, 0, 294, 105);
        add(label);

        // ... (中间的 add 组件代码省略，和原来一样) ...
        // 为了节省篇幅，这里假设中间的 add 代码与原文件一致

        // 简写重新定义组件以防万一
        lb_number = new JLabel("编号："); lb_number.setFont(new Font("宋体", Font.BOLD, 35)); lb_number.setBounds(293, 137, 194, 55); add(lb_number);
        tf_number = new JTextField(); tf_number.setFont(new Font("宋体", Font.BOLD, 35)); tf_number.setBounds(486, 127, 526, 75); add(tf_number);

        lb_classNumber = new JLabel("分类编号："); lb_classNumber.setFont(new Font("宋体", Font.BOLD, 35)); lb_classNumber.setBounds(293, 233, 194, 55); add(lb_classNumber);
        tf_classNumber = new JTextField(); tf_classNumber.setFont(new Font("宋体", Font.BOLD, 35)); tf_classNumber.setBounds(486, 223, 526, 75); add(tf_classNumber);

        lb_name = new JLabel("书名："); lb_name.setFont(new Font("宋体", Font.BOLD, 35)); lb_name.setBounds(293, 329, 194, 55); add(lb_name);
        tf_name = new JTextField(); tf_name.setFont(new Font("宋体", Font.BOLD, 35)); tf_name.setBounds(486, 319, 526, 75); add(tf_name);

        lb_className = new JLabel("分类名称："); lb_className.setFont(new Font("宋体", Font.BOLD, 35)); lb_className.setBounds(293, 435, 194, 55); add(lb_className);
        tf_className = new JTextField(); tf_className.setFont(new Font("宋体", Font.BOLD, 35)); tf_className.setBounds(486, 415, 526, 75); add(tf_className);

        lb_price = new JLabel("价格："); lb_price.setFont(new Font("宋体", Font.BOLD, 35)); lb_price.setBounds(293, 521, 194, 55); add(lb_price);
        tf_price = new JTextField(); tf_price.setFont(new Font("宋体", Font.BOLD, 35)); tf_price.setBounds(486, 511, 526, 75); add(tf_price);

        lb_state = new JLabel("入藏状态："); lb_state.setFont(new Font("宋体", Font.BOLD, 35)); lb_state.setBounds(293, 617, 194, 55); add(lb_state);
        tf_state = new JTextField(); tf_state.setFont(new Font("宋体", Font.BOLD, 35)); tf_state.setBounds(486, 607, 526, 75); add(tf_state);

        lb_total = new JLabel("数量："); lb_total.setFont(new Font("宋体", Font.BOLD, 35)); lb_total.setBounds(293, 713, 194, 55); add(lb_total);
        tf_total = new JTextField(); tf_total.setFont(new Font("宋体", Font.BOLD, 35)); tf_total.setBounds(486, 703, 526, 75); add(tf_total);

        btn_submit = new JButton("提交");
        btn_submit.setFont(new Font("宋体", Font.BOLD, 35));
        btn_submit.setBounds(573, 833, 251, 80);
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
        if (e.getSource() == back) {
            MainInterface.Insert_to_Manager();
        } else if (e.getSource() == btn_submit) {
            // 判定录入信息
            String number = tf_number.getText();
            String classNumber = tf_classNumber.getText();
            String name = tf_name.getText();
            String className = tf_className.getText();
            String price = tf_price.getText();
            String state = tf_state.getText();
            String total = tf_total.getText();

            // 逻辑修改：不需要传入 className 去查状态，只需要查 ID 是否存在
            if (sqlConn.search_bookState(number).equals("null")) {
                // 插入书籍信息
                sqlConn.insertBook(number, classNumber, name, className, price, state, total);

                // 清空输入框
                tf_number.setText("");
                tf_classNumber.setText("");
                tf_name.setText("");
                tf_className.setText("");
                tf_price.setText("");
                tf_state.setText("");
                tf_total.setText("");
                JOptionPane.showConfirmDialog(null, "恭喜", "书籍录入成功", JOptionPane.OK_CANCEL_OPTION);
            } else {
                JOptionPane.showMessageDialog(null,"该编号书籍已存在","书籍录入失败",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}