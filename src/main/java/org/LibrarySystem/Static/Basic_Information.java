package org.LibrarySystem.Static;

import org.LibrarySystem.Book;

import java.util.ArrayList;

public class Basic_Information {
    // 8.x驱动类名（若用5.x，替换为com.mysql.jdbc.Driver）
    public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    // JDBC连接URL：localhost=本地，3306=默认端口，mydb=你的数据库名
    // 8.x必须指定时区serverTimezone=Asia/Shanghai，解决时区报错
    public static final String DBURL
            = "jdbc:mysql://localhost:3306/BookManageSYS?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";    public static final String DBUSER = "root"; // MySQL用户名
    public static final String DBPASS = "050415"; // 替换为你的数据库密码

    //查询数据库图书类别名称
    public static String search_className = "";
    //查询数据库图书列表
    public static ArrayList<Book> bookArray = new ArrayList<Book>();
    //当前管理员
    public static String manager = "";
    //当前用户
    public static String user = "";
    //查询Table列表
    public static ArrayList<String> tableArray = new ArrayList<String>();
}
