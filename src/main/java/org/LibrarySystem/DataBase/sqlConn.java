package org.LibrarySystem.DataBase;

import org.LibrarySystem.Book;
import org.LibrarySystem.Static.Basic_Information;

import java.sql.*;
import java.util.ArrayList;

public class sqlConn {

    // 类加载时只加载一次驱动
    static {
        try {
            Class.forName(Basic_Information.DBDRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载数据库驱动失败", e);
        }
    }

    // 私有辅助方法,数据库连接
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Basic_Information.DBURL, Basic_Information.DBUSER, Basic_Information.DBPASS);
    }

    // 构造函数测试连接（保留原逻辑）
    public sqlConn() {

    }


    private static boolean checkUserExist(String table, String userCol, String pwdCol, String user, String password) {
        String sql = "SELECT count(*) FROM " + table + " WHERE " + userCol + " = ? AND " + pwdCol + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkUserNameExist(String table, String userCol, String user) {
        String sql = "SELECT count(*) FROM " + table + " WHERE " + userCol + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void insertUser(String table, String user, String password) {
        String sql = "INSERT INTO " + table + " VALUES(?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePassword(String table, String pwdCol, String userCol, String user, String newpassword) {
        String sql = "UPDATE " + table + " SET " + pwdCol + " = ? WHERE " + userCol + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newpassword);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 登录/注册/改密接口
    public static boolean is_User(String user, String password) {
        return checkUserExist("Customer", "customer_user", "customer_password", user, password);
    }
    public static boolean is_Manager(String user, String password) {
        return checkUserExist("Manager", "manager_user", "manager_password", user, password);
    }
    public static void register_newUser(String user, String password) {
        insertUser("Customer", user, password);
    }
    public static void register_newManager(String user, String password) {
        insertUser("Manager", user, password);
    }
    public static void changePassword_User(String user, String password, String newPassword) {
        updatePassword("Customer", "customer_password", "customer_user", user, newPassword);
    }
    public static void changePassword_Manager(String user, String password, String newPassword) {
        updatePassword("Manager", "manager_password", "manager_user", user, newPassword);
    }
    public static boolean is_user_Identity(String user, String password) {
        return checkUserNameExist("Customer", "customer_user", user);
    }
    public static boolean is_manager_Identity(String user, String password) {
        return checkUserNameExist("Manager", "manager_user", user);
    }

    /* --- 图书管理核心逻辑  --- */

    // 插入新书 (统一插入到 Book 表)
    public static void insertBook(String number, String classnumber, String name, String classname, String price, String state, String total) {
        String sql = "INSERT INTO Book (number, classnumber, name, classname, price, state, total, current, dateon, dateoff) VALUES(?, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, classnumber);
            pstmt.setString(3, name);
            pstmt.setString(4, classname); // 这里 classname 只是一个字段值
            pstmt.setString(5, price);
            pstmt.setString(6, state);
            pstmt.setString(7, total);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("插入书籍失败: " + e.getMessage());
        }
    }


    // 删除图书信息
    public static void deleteBook(String number) {
        // 只需要 ID 即可删除
        String sql = "DELETE FROM Book WHERE number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询图书
    public static void search_className(String classname) {
        Basic_Information.bookArray.clear();
        String sql;
        boolean queryAll = (classname == null || classname.isEmpty() || classname.equalsIgnoreCase("all"));

        if (queryAll) {
            sql = "SELECT * FROM Book";
        } else {
            sql = "SELECT * FROM Book WHERE classname = ?";
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (!queryAll) {
                pstmt.setString(1, classname);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.number = rs.getString("number");
                    book.classNumber = rs.getString("classnumber");
                    book.name = rs.getString("name");
                    book.className = rs.getString("classname");
                    book.price = rs.getString("price");
                    book.state = rs.getString("state");
                    book.total = rs.getString("total");
                    book.current = rs.getString("current");
                    book.dateOn = rs.getString("dateon");
                    book.dateOff = rs.getString("dateoff");
                    Basic_Information.bookArray.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 借还书逻辑

    public static void borrowBook_Update(String number, String user, String dateoff) {
        String sql = "UPDATE Book SET state = 'out', current = ?, dateoff = ? WHERE number = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, dateoff);
            pstmt.setString(3, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // 还书：更新 Book 表状态 (清空借阅人信息)
    public static void returnBook_Update(String number) {
        String sql = "UPDATE Book SET state = 'in', current = NULL, dateoff = NULL WHERE number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // 续借：只更新 Book 表的 dateoff
    public static void prolongBook_Update(String number, String dateoff) {
        String sql = "UPDATE Book SET dateoff = ? WHERE number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dateoff);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询个人借阅信息 (查 Book 表 current 字段)
    public static void search_user(String user) {
        Basic_Information.bookArray.clear();
        String sql = "SELECT * FROM Book WHERE current = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.number = rs.getString("number");
                    book.classNumber = rs.getString("classnumber");
                    book.name = rs.getString("name");
                    book.className = rs.getString("classname");
                    book.dateOff = rs.getString("dateoff");
                    // ... 其他字段按需取
                    Basic_Information.bookArray.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 辅助：获取单个值
    private static String getSingleValue(String number, String columnLabel) {
        String sql = "SELECT " + columnLabel + " FROM Book WHERE number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String val = rs.getString(1); // column index 1
                    return val == null ? "null" : val;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null"; // Not found or error
    }

    // 查找书名
    public static String search_bookName(String number) {
        return getSingleValue(number, "name");
    }

    // 查找书籍状态
    public static String search_bookState(String number) {
        return getSingleValue(number, "state");
    }

    // 查找书籍还书日期
    public static int search_bookDateOff(String number) {
        String val = getSingleValue(number, "dateoff");
        try {
            return (val == null || "null".equals(val)) ? 0 : Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}