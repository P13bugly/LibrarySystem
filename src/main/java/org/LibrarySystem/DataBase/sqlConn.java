package org.LibrarySystem.DataBase;

import org.LibrarySystem.Book;
import org.LibrarySystem.Static.Basic_Information;

import java.sql.*;

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

    public sqlConn() {
        String sql = "select * from temp";
        // try-with-resources program
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("成功连接到数据库!");
            System.out.println("编号\t姓名\t位置");
            while (rs.next()) {
                System.out.print(rs.getString(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.print(rs.getString(3) + "\t");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通用查询
    private static boolean checkUserExist(String table, String userCol, String pwdCol, String user, String password) {
        // 统计匹配数据记录
        String sql = "SELECT count(*) FROM " + table + " WHERE " + userCol + " = ? AND " + pwdCol + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 检查用户名是否存在(不校验密码)
    private static boolean checkUserNameExist(String table, String userCol, String user) {
        String sql = "SELECT count(*) FROM " + table + " WHERE " + userCol + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 通用更新方法
    private static void insertUser(String table, String user, String password) {
        String sql = "INSERT INTO " + table + " VALUES(?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 通用改密方法
    private static void updatePassword(String table, String pwdCol, String userCol, String user, String newpassword) {
        String sql = "UPDATE " + table + " SET " + pwdCol + " = ? WHERE " + userCol + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newpassword);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 通用单值查询 helper
    private static String getSingleValue(String classname, String number, String columnLabel) {
        String sql = "SELECT * FROM " + classname + " WHERE number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(columnLabel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null";
    }

    // 查询用户存在
    public static boolean is_User(String user, String password) {
        return checkUserExist("Customer", "customer_user", "customer_password", user, password);
    }

    // 查询管理员存在
    public static boolean is_Manager(String user, String password) {
        return checkUserExist("Manager", "manager_user", "manager_password", user, password);
    }

    // 注册新用户
    public static void register_newUser(String user, String password) {
        insertUser("Customer", user, password);
    }

    // 注册新管理员
    public static void register_newManager(String user, String password) {
        insertUser("Manager", user, password);
    }

    // 改变用户pw
    public static void changePassword_User(String user, String password, String newPassword) {
        updatePassword("Customer", "customer_password", "customer_user", user, newPassword);
    }

    // 改变管理员ps
    public static void changePassword_Manager(String user, String password, String newPassword) {
        updatePassword("Manager", "manager_password", "manager_user", user, newPassword);
    }

    // 用户身份是否存在
    public static boolean is_user_Identity(String user, String password) {
        return checkUserNameExist("Customer", "customer_user", user);
    }

    // 管理员身份是否存在
    public static boolean is_manager_Identity(String user, String password) {
        return checkUserNameExist("Manager", "manager_user", user);
    }

    // 插入新书
    public static void insertBook(String number, String classnumber, String name, String classname, String price, String state, String total) {
        String sql = "INSERT INTO " + classname + " VALUES(?, ?, ?, ?, ?, ?, ?,? ,? ,? )";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, classnumber);
            pstmt.setString(3, name);
            pstmt.setString(4, classname);
            pstmt.setString(5, price);
            pstmt.setString(6, state);
            pstmt.setString(7, total);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 新建图书类别
    public static void newClass(String classnumber, String classname) {
        String sql = "CREATE TABLE " + classname + " (number VARCHAR(255) primary key, classnumber VARCHAR(255), " +
                "name VARCHAR(255), classname VARCHAR(255), price VARCHAR(255), state VARCHAR(255), " +
                "total VARCHAR(255), current VARCHAR(255), dateon VARCHAR(255), dateoff VARCHAR(255))";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除图书信息
    public static void deleteBook(String number, String classname) {
        String sql = "DELETE FROM " + classname + " WHERE number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询某类图书信息
    public static void search_className(String classname) {
        Basic_Information.bookArray.clear();
        String sql = "SELECT * FROM " + classname;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.number = rs.getString(1);
                book.classNumber = rs.getString(2);
                book.name = rs.getString(3);
                book.className = rs.getString(4);
                book.price = rs.getString(5);
                book.state = rs.getString(6);
                book.total = rs.getString(7);
                book.current = rs.getString(8);
                book.dateOn = rs.getString(9);
                book.dateOff = rs.getString(10);
                Basic_Information.bookArray.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 借书信息变更
    public static void borrowBook_Update(String classname, String number, String user, String dateoff) {
        String sql = "UPDATE " + classname + " SET state = 'out', current = ?, dateoff = ? WHERE number = ?";

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



    // 借书插入到借书记录
    public static void borrowBook_Insert(String classname, String number, String user, String dateoff) {
        String bookName = search_bookName(classname, number);
        String sql = "INSERT INTO borrowrecords (number, classname, name, dateoff, username) VALUES(?, ?, ?, ?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, classname);
            pstmt.setString(3, bookName);
            pstmt.setString(4, dateoff);
            pstmt.setString(5,user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 还书更新图书数据库
    public static void returnBook_BookUpdate(String classname, String number, String user, String dateoff) {
        String sql = "UPDATE " + classname + " SET state = 'in', current = 'null', dateoff = 'null' WHERE number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 还书更新借书记录(delete)
    public static void returnBook_UserUpdate(String number, String user) {
        String sql = "DELETE FROM borrowrecords WHERE number = ? AND username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2,user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 续借更新两者数据库信息 (使用事务)
    public static void prolongBook_Update(String classname, String number, String dateoff, String user) {
        String sqlBook = "UPDATE " + classname + " SET dateoff = ? WHERE number = ?";
        String sqlUser = "UPDATE borrowrecords SET dateoff = ? WHERE number = ? AND username = ?";

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // 开启事务

            try (PreparedStatement pst1 = conn.prepareStatement(sqlBook);
                 PreparedStatement pst2 = conn.prepareStatement(sqlUser)) {

                // 更新书籍表
                pst1.setString(1, dateoff);
                pst1.setString(2, number);
                pst1.executeUpdate();

                // 更新借阅记录
                pst2.setString(1, dateoff);
                pst2.setString(2, number);
                pst2.setString(3,user);
                pst2.executeUpdate();
            }

            conn.commit(); // 提交事务
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // 出现异常回滚
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 恢复默认
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 查询个人图书信息
    public static void search_user(String user) {
        Basic_Information.bookArray.clear();

        String sql = "SELECT number, classname, name, dateoff FROM BorrowRecords WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.number = rs.getString("number");
                    book.className = rs.getString("classname");
                    book.name = rs.getString("name");
                    book.dateOff = rs.getString("dateoff");

                    Basic_Information.bookArray.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 是否存在Table
    public static boolean is_Table(String table) {
        try (Connection conn = getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, table, null)) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 查找书名
    public static String search_bookName(String classname, String number) {
        return getSingleValue(classname, number, "name");
    }

    // 查找书籍状态
    public static String search_bookState(String classname, String number) {
        return getSingleValue(classname, number, "state");
    }

    // 查找书籍还书日期
    public static int search_bookDateOff(String classname, String number) {
        String val = getSingleValue(classname, number, "dateoff");
        try {
            return "null".equals(val) || val == null ? 0 : Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}