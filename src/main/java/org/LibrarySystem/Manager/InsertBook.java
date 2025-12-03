package org.LibrarySystem.Manager;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertBook extends JPanel implements ActionListener {
    //书籍信息交互
    private JTextField tf_number;      //书籍号
    private JTextField tf_classNumber; //类别号
    private JTextField tf_name;        //书籍名称
    private JTextField tf_className;   //类别名称
    private JTextField tf_price;       //价格
    private JTextField tf_state;       //书籍状态
    private JTextField tf_total;       //数量
    //书籍标签
    private JLabel lb_number;
    private JLabel lb_classNumber;
    private JLabel lb_name;
    private JLabel lb_className;
    private JLabel lb_price;
    private JLabel lb_state;
    private JLabel lb_total;
    //交互:返回,提交
    private JButton back;
    private JButton btn_submit;

    public InsertBook(){
        setBackground(Color.PINK);
        setLayout(null);

        JLabel label = new JLabel("新书录入");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(530, 0, 294, 105);
        add(label);

        lb_number = new JLabel("编号：");
        lb_number.setFont(new Font("宋体", Font.BOLD, 35));
        lb_number.setBounds(293, 137, 194, 55);
        add(lb_number);

        tf_number = new JTextField();
        tf_number.setFont(new Font("宋体", Font.BOLD, 35));
        tf_number.setColumns(10);
        tf_number.setBounds(486, 127, 526, 75);
        add(tf_number);

        lb_classNumber = new JLabel("分类编号：");
        lb_classNumber.setFont(new Font("宋体", Font.BOLD, 35));
        lb_classNumber.setBounds(293, 233, 194, 55);
        add(lb_classNumber);

        lb_name = new JLabel("书名：");
        lb_name.setFont(new Font("宋体", Font.BOLD, 35));
        lb_name.setBounds(293, 329, 194, 55);
        add(lb_name);

        lb_className = new JLabel("分类名称：");
        lb_className.setFont(new Font("宋体", Font.BOLD, 35));
        lb_className.setBounds(293, 435, 194, 55);
        add(lb_className);

        lb_price = new JLabel("价格：");
        lb_price.setFont(new Font("宋体", Font.BOLD, 35));
        lb_price.setBounds(293, 521, 194, 55);
        add(lb_price);

        lb_state = new JLabel("入藏状态：");
        lb_state.setFont(new Font("宋体", Font.BOLD, 35));
        lb_state.setBounds(293, 617, 194, 55);
        add(lb_state);

        lb_total = new JLabel("数量：");
        lb_total.setFont(new Font("宋体", Font.BOLD, 35));
        lb_total.setBounds(293, 713, 194, 55);
        add(lb_total);

        tf_classNumber = new JTextField();
        tf_classNumber.setFont(new Font("宋体", Font.BOLD, 35));
        tf_classNumber.setColumns(10);
        tf_classNumber.setBounds(486, 223, 526, 75);
        add(tf_classNumber);

        tf_name = new JTextField();
        tf_name.setFont(new Font("宋体", Font.BOLD, 35));
        tf_name.setColumns(10);
        tf_name.setBounds(486, 319, 526, 75);
        add(tf_name);

        tf_className = new JTextField();
        tf_className.setFont(new Font("宋体", Font.BOLD, 35));
        tf_className.setColumns(10);
        tf_className.setBounds(486, 415, 526, 75);
        add(tf_className);

        tf_price = new JTextField();
        tf_price.setFont(new Font("宋体", Font.BOLD, 35));
        tf_price.setColumns(10);
        tf_price.setBounds(486, 511, 526, 75);
        add(tf_price);

        tf_state = new JTextField();
        tf_state.setFont(new Font("宋体", Font.BOLD, 35));
        tf_state.setColumns(10);
        tf_state.setBounds(486, 607, 526, 75);
        add(tf_state);

        tf_total = new JTextField();
        tf_total.setFont(new Font("宋体", Font.BOLD, 35));
        tf_total.setColumns(10);
        tf_total.setBounds(486, 703, 526, 75);
        add(tf_total);

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
            //判定录入信息
            String number = tf_number.getText();
            String classNumber = tf_classNumber.getText();
            String name = tf_name.getText();
            String className = tf_className.getText();
            String price = tf_price.getText();
            String state = tf_state.getText();
            String total = tf_total.getText();
            if (sqlConn.search_bookState(className, number).equals("null")) {
                //插入书籍信息
                sqlConn.insertBook(number, classNumber, name, className, price, state, total);
                tf_number.setText("");
                tf_classNumber.setText("");
                tf_name.setText("");
                tf_className.setText("");
                tf_price.setText("");
                tf_state.setText("");
                tf_total.setText("");
                JOptionPane.showConfirmDialog(null, "恭喜", "书籍录入成功", JOptionPane.OK_CANCEL_OPTION);
            }else {
                JOptionPane.showMessageDialog(null,"书籍信息已存在","书籍录入失败",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
