package org.LibrarySystem.Main_UI;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Manager.*;
import org.LibrarySystem.Static.Basic_Information;
import org.LibrarySystem.User.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInterface extends JFrame implements ActionListener {

    // Main Content Pane
    public static JPanel contentPane;

    // Manager Panels
    public static ManagerInterface managerJPanel = new ManagerInterface();  // Manager UI
    public static InsertBook insertJPanel = new InsertBook();    // 插入新书
    public static UpdateBook updateJPanel = new UpdateBook();    // 更新书籍
    public static State_search stateJPanel = new State_search(); // 书籍状态查询
    public static State_Output stateInfoJPanel = new State_Output(); // 书籍状态详情

    // User Panels
    public static UserInterface userJPanel = new UserInterface();    // User UI
    public static BorrowBook borrowJPanel = new BorrowBook();        // 借书
    public static BorrowBook_Output borrowInfoJPanel = new BorrowBook_Output(); // 借书信息
    public static ReturnBook returnJPanel = new ReturnBook();        // 还书
    public static ProlongBook prolongJPanel = new ProlongBook();     // 延长书籍使用时间
    public static PersonalInfo personalJPanel = new PersonalInfo();  // 我的个人信息

    // 主界面组件
    private JTextField tf_user;             // 用户名
    private JPasswordField pf_password;     // 密码
    private JLabel label_user;              // 用户名标签
    private JLabel label_password;          // 密码标签
    private JRadioButton rb_user;           // 用户加入单选按钮组
    private JRadioButton rb_manager;        // 管理员加入单选按钮组
    private JButton btn_login;              // 登录
    private JButton btn_register;           // 注册
    private JButton btn_changePassword;     // 更改密码
    private JLabel label;
    private static Container container;

    // 页面状态标志位
    private static boolean flag_manager = false;
    private static boolean flag_insert = false;
    // private static boolean flag_newClass = false; // [已删除]
    private static boolean flag_update = false;
    private static boolean flag_state = false;
    private static boolean flag_stateInfo = false;
    private static boolean flag_user = false;
    private static boolean flag_borrow = false;
    private static boolean flag_borrowInfo = false;
    private static boolean flag_return = false;
    private static boolean flag_prolong = false;
    private static boolean flag_personal = false;

    public MainInterface(){
        init();
    }

    public void init(){
        container = getContentPane(); // 主界面容器

        // init Main interface
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(800, 400, 1300, 1000);
        setResizable(false);

        // 面板设置
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.add(contentPane);
        contentPane.setLayout(null);

        // 账号输入
        tf_user = new JTextField();
        tf_user.setFont(new Font("宋体", Font.BOLD, 35));
        tf_user.setBounds(409, 248, 526, 75);
        contentPane.add(tf_user);
        tf_user.setColumns(10);

        // 密码输入
        pf_password = new JPasswordField();
        pf_password.setFont(new Font("宋体", Font.BOLD, 35));
        pf_password.setColumns(10);
        pf_password.setBounds(409, 357, 526, 75);
        contentPane.add(pf_password);

        // 用户名标签
        label_user = new JLabel("账号：");
        label_user.setFont(new Font("宋体", Font.BOLD, 35));
        label_user.setBounds(289, 261, 116, 48);
        contentPane.add(label_user);

        // 密码标签
        label_password = new JLabel("密码：");
        label_password.setFont(new Font("宋体", Font.BOLD, 35));
        label_password.setBounds(289, 370, 116, 48);
        contentPane.add(label_password);

        // 普通用户单选按钮
        rb_user = new JRadioButton("普通用户");
        rb_user.setSelected(true);
        rb_user.setFont(new Font("宋体", Font.BOLD, 30));
        rb_user.setBackground(Color.WHITE);
        rb_user.setBounds(409, 515, 170, 37);
        contentPane.add(rb_user);

        // 管理人员单选按钮
        rb_manager = new JRadioButton("管理人员");
        rb_manager.setFont(new Font("宋体", Font.BOLD, 30));
        rb_manager.setBackground(Color.WHITE);
        rb_manager.setBounds(765, 515, 170, 37);
        contentPane.add(rb_manager);

        // 添加到一个按钮组
        ButtonGroup bg = new ButtonGroup();
        bg.add(rb_user);
        bg.add(rb_manager);

        // 登录按钮
        btn_login = new JButton("登录");
        btn_login.setBackground(Color.LIGHT_GRAY);
        btn_login.setFont(new Font("宋体", Font.BOLD, 35));
        btn_login.setBounds(409, 634, 170, 48);
        contentPane.add(btn_login);
        btn_login.addActionListener(this);

        // 注册按钮
        btn_register = new JButton("注册");
        btn_register.setFont(new Font("宋体", Font.BOLD, 35));
        btn_register.setBackground(Color.LIGHT_GRAY);
        btn_register.setBounds(765, 634, 170, 48);
        contentPane.add(btn_register);
        btn_register.addActionListener(this);

        // 修改密码按钮
        btn_changePassword = new JButton("修改密码");
        btn_changePassword.setFont(new Font("宋体", Font.BOLD, 30));
        btn_changePassword.setBackground(Color.LIGHT_GRAY);
        btn_changePassword.setBounds(1011, 370, 170, 48);
        contentPane.add(btn_changePassword);
        btn_changePassword.addActionListener(this);

        label = new JLabel("图书管理系统");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 40));
        label.setBounds(529, 69, 294, 105);
        contentPane.add(label);
    }

    // ------------------ 切换窗口逻辑 ------------------

    // Main --> Manager
    public static void Main_to_Manager(){
        if(!flag_manager){
            contentPane.setVisible(false);
            container.add(managerJPanel);
            flag_manager = true;
        } else {
            contentPane.setVisible(false);
            managerJPanel.setVisible(true);
        }
    }
    // Manager --> Main
    public static void Manager_to_Main(){
        managerJPanel.setVisible(false);
        contentPane.setVisible(true);
    }

    // Manager --> Insert
    public static void Manager_to_Insert() {
        if(!flag_insert) {
            managerJPanel.setVisible(false);
            container.add(insertJPanel);
            flag_insert = true;
        } else {
            managerJPanel.setVisible(false);
            insertJPanel.setVisible(true);
        }
    }
    // Insert --> Manager
    public static void Insert_to_Manager() {
        insertJPanel.setVisible(false);
        managerJPanel.setVisible(true);
    }


    // Manager --> UpdateBook
    public static void Manager_to_Update() {
        if(!flag_update) {
            managerJPanel.setVisible(false);
            container.add(updateJPanel);
            flag_update = true;
        } else {
            managerJPanel.setVisible(false);
            updateJPanel.setVisible(true);
        }
    }
    // UpdateBook --> Manager
    public static void Update_to_Manager() {
        updateJPanel.setVisible(false);
        managerJPanel.setVisible(true);
    }

    // Manager --> State
    public static void Manager_to_State() {
        if(!flag_state) {
            managerJPanel.setVisible(false);
            container.add(stateJPanel);
            flag_state = true;
        } else {
            managerJPanel.setVisible(false);
            stateJPanel.setVisible(true);
        }
    }
    // State --> Manager
    public static void State_to_Manager() {
        stateJPanel.setVisible(false);
        managerJPanel.setVisible(true);
    }

    // State --> StateInfo
    public static void State_to_StateInfo() {
        if(!flag_stateInfo) {
            stateJPanel.setVisible(false);
            container.add(stateInfoJPanel);
            flag_stateInfo = true;
        } else {
            stateJPanel.setVisible(false);
            stateInfoJPanel.setVisible(true);
        }
    }
    // StateInfo --> State
    public static void StateInfo_to_State() {
        stateInfoJPanel.setVisible(false);
        stateJPanel.setVisible(true);
    }

    // Main -> User
    public static void Main_to_User() {
        if(!flag_user) {
            contentPane.setVisible(false);
            container.add(userJPanel);
            flag_user = true;
        } else {
            contentPane.setVisible(false);
            userJPanel.setVisible(true);
        }
    }
    // User --> Main
    public static void User_to_Main() {
        userJPanel.setVisible(false);
        contentPane.setVisible(true);
    }

    // User -> Borrow
    public static void User_to_Borrow() {
        if(!flag_borrow) {
            userJPanel.setVisible(false);
            container.add(borrowJPanel);
            flag_borrow = true;
        } else {
            userJPanel.setVisible(false);
            borrowJPanel.setVisible(true);
        }
    }
    // Borrow --> User
    public static void Borrow_to_User() {
        borrowJPanel.setVisible(false);
        userJPanel.setVisible(true);
    }

    // Borrow --> BorrowInfo
    public static void Borrow_to_BorrowInfo() {
        if(!flag_borrowInfo) {
            borrowJPanel.setVisible(false);
            container.add(borrowInfoJPanel);
            flag_borrowInfo = true;
        } else {
            borrowJPanel.setVisible(false);
            borrowInfoJPanel.setVisible(true);
        }
    }
    // BorrowInfo --> Borrow
    public static void BorrowInfo_to_Borrow() {
        borrowInfoJPanel.setVisible(false);
        borrowJPanel.setVisible(true);
    }

    // User --> Return
    public static void User_to_Return() {
        if(!flag_return) {
            userJPanel.setVisible(false);
            container.add(returnJPanel);
            flag_return = true;
        } else {
            userJPanel.setVisible(false);
            returnJPanel.setVisible(true);
        }
    }
    // Return -> User
    public static void Return_to_User() {
        returnJPanel.setVisible(false);
        userJPanel.setVisible(true);
    }

    // User -> Prolong
    public static void User_to_ProlongBook() {
        if(!flag_prolong) {
            userJPanel.setVisible(false);
            container.add(prolongJPanel);
            flag_prolong = true;
        } else {
            userJPanel.setVisible(false);
            prolongJPanel.setVisible(true);
        }
    }
    // Prolong --> User
    public static void ProlongBook_to_User() {
        prolongJPanel.setVisible(false);
        userJPanel.setVisible(true);
    }

    // User --> Personal
    public static void User_to_Personal() {
        if(!flag_personal) {
            userJPanel.setVisible(false);
            container.add(personalJPanel);
            flag_personal = true;
        } else {
            userJPanel.setVisible(false);
            personalJPanel.setVisible(true);
        }
    }
    // Personal --> User
    public static void Personal_to_User() {
        personalJPanel.setVisible(false);
        userJPanel.setVisible(true);
    }

    // ------------------ 事件监听 ------------------

    @Override
    public void actionPerformed(ActionEvent e) {
        String tpass = new String(pf_password.getPassword());

        // 登录
        if(e.getSource() == btn_login){
            if(tf_user.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"用户名为空","登录失败",JOptionPane.ERROR_MESSAGE);
            } else if (tpass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "密码为空", "登录失败", JOptionPane.ERROR_MESSAGE);
            } else {
                // 识别身份
                if(rb_user.isSelected()){
                    // User
                    if(sqlConn.is_User(tf_user.getText(), tpass)){
                        JOptionPane.showConfirmDialog(null,"欢迎使用","用户登录成功",JOptionPane.OK_CANCEL_OPTION);
                        Basic_Information.user = tf_user.getText();
                        UserInterface.setUser();
                        Main_to_User();
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确的用户名密码", "用户登录失败", JOptionPane.ERROR_MESSAGE);
                    }
                } else if(rb_manager.isSelected()) {
                    // Manager
                    if(sqlConn.is_Manager(tf_user.getText(), tpass)){
                        JOptionPane.showConfirmDialog(null,"欢迎使用","管理员登录成功",JOptionPane.OK_CANCEL_OPTION);
                        Basic_Information.manager = tf_user.getText();
                        ManagerInterface.setManger();
                        Main_to_Manager();
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确的用户名密码", "管理员登录失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        // 注册
        else if(e.getSource() == btn_register) {
            if (tf_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "用户名不能为空", "注册失败", JOptionPane.ERROR_MESSAGE);
            } else if (tpass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "密码不能为空", "注册失败", JOptionPane.ERROR_MESSAGE);
            } else {
                // User 注册
                if(rb_user.isSelected()) {
                    if(sqlConn.is_user_Identity(tf_user.getText(), tpass)) {
                        JOptionPane.showMessageDialog(null, "该用户已经存在", "用户注册失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showConfirmDialog(null, "欢迎！", "用户注册成功", JOptionPane.OK_CANCEL_OPTION);
                        sqlConn.register_newUser(tf_user.getText(),  tpass);
                    }
                }
                // Manager 注册
                else if(rb_manager.isSelected()){
                    if(sqlConn.is_manager_Identity(tf_user.getText(), tpass)) {
                        JOptionPane.showMessageDialog(null, "该管理员已经存在", "管理员注册失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showConfirmDialog(null, "欢迎！", "管理员注册成功", JOptionPane.OK_CANCEL_OPTION);
                        sqlConn.register_newManager(tf_user.getText(), tpass);
                    }
                }
            }
        }
        // 修改密码
        else if(e.getSource() == btn_changePassword) {
            if (tf_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "用户名不能为空", "修改失败", JOptionPane.ERROR_MESSAGE);
            } else if (tpass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "密码不能为空", "修改失败", JOptionPane.ERROR_MESSAGE);
            } else {
                if(rb_user.isSelected()) {
                    // User
                    if(sqlConn.is_User(tf_user.getText(), tpass)) {
                        String newPassword = JOptionPane.showInputDialog("请输入新密码");
                        if (newPassword != null && !newPassword.isEmpty()) {
                            sqlConn.changePassword_User(tf_user.getText(), tpass, newPassword);
                            JOptionPane.showConfirmDialog(null, "欢迎使用", "修改密码成功", JOptionPane.OK_CANCEL_OPTION);
                        } else {
                            JOptionPane.showMessageDialog(null, "密码不能为空", "修改密码失败", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确的用户名密码", "修改密码失败", JOptionPane.ERROR_MESSAGE);
                    }
                } else if(rb_manager.isSelected()){
                    // Manager
                    boolean isExist = sqlConn.is_Manager(tf_user.getText(), tpass);
                    if(isExist) {
                        String newPassword = JOptionPane.showInputDialog("请输入新密码");
                        if (newPassword != null && !newPassword.isEmpty()) {
                            sqlConn.changePassword_Manager(tf_user.getText(), tpass, newPassword);
                            JOptionPane.showConfirmDialog(null, "欢迎使用", "修改密码成功", JOptionPane.OK_CANCEL_OPTION);
                        } else {
                            JOptionPane.showMessageDialog(null, "密码不能为空", "修改密码失败", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确的用户名密码", "修改密码失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
