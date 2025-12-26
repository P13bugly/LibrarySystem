package org.LibrarySystem.Manager;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateBook extends JPanel implements ActionListener {
    //旧书籍信息
    private JTextField tf_old_number;   //旧编号
    private JTextField tf_old_className;//旧分类名称 (其实现在数据库逻辑不需要了，但UI保留)

    //新书籍信息
    private JTextField tf_new_number;
    private JTextField tf_new_classNumber;
    private JTextField tf_new_name;
    private JTextField tf_new_className;
    private JTextField tf_new_price;
    private JTextField tf_new_state;

    // UI 组件
    private JLabel update, label, lb_old_number, lb_old_className, lb_new_number, lb_new_classNumber, lb_new_name, lb_new_clasName, lb_new_price, lb_new_state;
    private JButton btn_submit, back;

    public UpdateBook() {
        // ... (UI 布局代码保持不变，请复制原有的构造函数内容) ...
        setBackground(new Color(230, 230, 250));
        setLayout(null);

        label = new JLabel("更新图书信息"); label.setHorizontalAlignment(SwingConstants.CENTER); label.setFont(new Font("宋体", Font.BOLD, 40)); label.setBounds(530, 20, 294, 105); add(label);

        lb_old_number = new JLabel("旧编号："); lb_old_number.setFont(new Font("宋体", Font.BOLD, 35)); lb_old_number.setBounds(165, 127, 194, 55); add(lb_old_number);
        tf_old_number = new JTextField(); tf_old_number.setFont(new Font("宋体", Font.BOLD, 35)); tf_old_number.setBounds(298, 117, 252, 75); add(tf_old_number);

        lb_old_className = new JLabel("旧分类(可选)："); // 提示用户
        lb_old_className.setFont(new Font("宋体", Font.BOLD, 35)); lb_old_className.setBounds(570, 127, 222, 55); add(lb_old_className);
        tf_old_className = new JTextField(); tf_old_className.setFont(new Font("宋体", Font.BOLD, 35)); tf_old_className.setBounds(788, 117, 342, 75); add(tf_old_className);

        update = new JLabel("更新信息"); update.setFont(new Font("宋体", Font.BOLD, 35)); update.setBounds(607, 213, 157, 55); add(update);

        // 新信息输入区域
        tf_new_number = new JTextField(); tf_new_number.setFont(new Font("宋体", Font.BOLD, 35)); tf_new_number.setBounds(507, 274, 526, 75); add(tf_new_number);
        tf_new_classNumber = new JTextField(); tf_new_classNumber.setFont(new Font("宋体", Font.BOLD, 35)); tf_new_classNumber.setBounds(507, 370, 526, 75); add(tf_new_classNumber);
        tf_new_name = new JTextField(); tf_new_name.setFont(new Font("宋体", Font.BOLD, 35)); tf_new_name.setBounds(507, 466, 526, 75); add(tf_new_name);
        tf_new_className = new JTextField(); tf_new_className.setFont(new Font("宋体", Font.BOLD, 35)); tf_new_className.setBounds(507, 562, 526, 75); add(tf_new_className);
        tf_new_price = new JTextField(); tf_new_price.setFont(new Font("宋体", Font.BOLD, 35)); tf_new_price.setBounds(507, 658, 526, 75); add(tf_new_price);
        tf_new_state = new JTextField(); tf_new_state.setFont(new Font("宋体", Font.BOLD, 35)); tf_new_state.setBounds(507, 754, 526, 75); add(tf_new_state);

        // Labels
        lb_new_number = new JLabel("新编号："); lb_new_number.setFont(new Font("宋体", Font.BOLD, 35)); lb_new_number.setBounds(285, 284, 194, 55); add(lb_new_number);
        lb_new_classNumber = new JLabel("新分类编号："); lb_new_classNumber.setFont(new Font("宋体", Font.BOLD, 35)); lb_new_classNumber.setBounds(285, 380, 227, 55); add(lb_new_classNumber);
        lb_new_name = new JLabel("新书名："); lb_new_name.setFont(new Font("宋体", Font.BOLD, 35)); lb_new_name.setBounds(285, 476, 194, 55); add(lb_new_name);
        lb_new_clasName = new JLabel("新分类名称："); lb_new_clasName.setFont(new Font("宋体", Font.BOLD, 35)); lb_new_clasName.setBounds(285, 572, 227, 55); add(lb_new_clasName);
        lb_new_price = new JLabel("新价格："); lb_new_price.setFont(new Font("宋体", Font.BOLD, 35)); lb_new_price.setBounds(285, 668, 227, 55); add(lb_new_price);
        lb_new_state = new JLabel("新状态："); lb_new_state.setFont(new Font("宋体", Font.BOLD, 35)); lb_new_state.setBounds(285, 764, 232, 55); add(lb_new_state);

        back = new JButton("返回"); back.setFont(new Font("宋体", Font.PLAIN, 26)); back.setBounds(0, 0, 115, 82); add(back); back.addActionListener(this);
        btn_submit = new JButton("提交"); btn_submit.setFont(new Font("宋体", Font.BOLD, 35)); btn_submit.setBounds(570, 850, 251, 80); add(btn_submit); btn_submit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back) {
            MainInterface.Update_to_Manager();
        } else if(e.getSource() == btn_submit) {
            //旧信息
            String old_number = tf_old_number.getText();
            // String old_className = tf_old_className.getText(); // 数据库重构后不需要它来定位了

            //新信息
            String number = tf_new_number.getText();
            String classNumber = tf_new_classNumber.getText();
            String name = tf_new_name.getText();
            String className = tf_new_className.getText();
            String price = tf_new_price.getText();
            String state = tf_new_state.getText();

            // 逻辑修改：
            // 1. 检查旧书是否存在 (只用 ID)
            // 2. 如果存在，删除旧的，插入新的
            if(!sqlConn.search_bookState(old_number).equals("null")) {
                // 删除旧书籍信息 (只需 ID)
                sqlConn.deleteBook(old_number);

                // 插入新书籍信息
                sqlConn.insertBook(number, classNumber, name, className, price, state, "1");

                // 文本刷新
                tf_old_className.setText("");
                tf_old_number.setText("");
                tf_new_number.setText("");
                tf_new_classNumber.setText("");
                tf_new_name.setText("");
                tf_new_className.setText("");
                tf_new_price.setText("");
                tf_new_state.setText("");
                JOptionPane.showConfirmDialog(null, "成功", "更新信息成功", JOptionPane.OK_CANCEL_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "未找到该编号的旧书", "更新信息失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}