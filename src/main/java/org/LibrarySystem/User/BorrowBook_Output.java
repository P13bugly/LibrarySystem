package org.LibrarySystem.User;

import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowBook_Output extends JPanel implements ActionListener {
    private JButton back;
    private JButton btn_borrow;
    private JTextField tf_number;
    private JTextField tf_dateOff;
    //table组件
    private static JTable bookTable;
    private static DefaultTableModel tableModel;

    public BorrowBook_Output(){
        setLayout(new BorderLayout(0,0));
        setBackground(new Color(240,255,240));

        JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,15,15));
        topPanel.setOpaque(false);

        back =new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 20));
        back.setPreferredSize(new Dimension(100, 40));
        back.addActionListener(this);
        topPanel.add(back);

        topPanel.add(Box.createHorizontalStrut(20));

        // 图书编号标签
        JLabel lb_number = new JLabel("图书编号：");
        lb_number.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(lb_number);

        // 图书编号输入框
        tf_number = new JTextField();
        tf_number.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        tf_number.setPreferredSize(new Dimension(120, 35));
        topPanel.add(tf_number);

        // 期限标签
        JLabel label_dateOff = new JLabel("期限：");
        label_dateOff.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(label_dateOff);

        // 期限输入框
        tf_dateOff = new JTextField();
        tf_dateOff.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        tf_dateOff.setPreferredSize(new Dimension(120, 35));
        topPanel.add(tf_dateOff);

        // 借书按钮
        btn_borrow = new JButton("借书");
        btn_borrow.setFont(new Font("微软雅黑", Font.BOLD, 16));
        btn_borrow.setPreferredSize(new Dimension(100, 40));
        btn_borrow.setBackground(new Color(100, 149, 237));
        btn_borrow.setForeground(Color.WHITE);
        btn_borrow.addActionListener(this);
        topPanel.add(btn_borrow);

        add(topPanel, BorderLayout.NORTH);

        //表头
        String[] columnNames = {
                "图书编号", "分类编号", "图书名称", "分类名称",
                "价格", "状态", "获取人", "终止日期"
        };
        //不可编辑
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        bookTable = new JTable(tableModel);
        bookTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        bookTable.setRowHeight(28);

        bookTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));
        bookTable.getTableHeader().setBackground(new Color(220, 220, 220));
        bookTable.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        bookTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(scrollPane, BorderLayout.CENTER);
    }

    public static void setTable(){
        tableModel.setRowCount(0);

        if (Basic_Information.bookArray != null) {
            for (int i = 0; i < Basic_Information.bookArray.size(); i++) {
                var book = Basic_Information.bookArray.get(i);
                Object[] rowData = {
                        book.number,
                        book.classNumber,
                        book.name,
                        book.className,
                        book.price,
                        book.state,
                        book.current,
                        book.dateOff
                };
                tableModel.addRow(rowData);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back){
            MainInterface.BorrowInfo_to_Borrow();
        } else if (e.getSource()==btn_borrow) {
            String bookId = tf_number.getText().trim();
            String dateOff = tf_dateOff.getText().trim();

            if(bookId.isEmpty()||dateOff.isEmpty()){
                JOptionPane.showMessageDialog(null,"请输入完整的图书编号和期限","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 修改：不再传入 Basic_Information.search_className
            if(sqlConn.search_bookName(bookId).equals("null")){
                JOptionPane.showMessageDialog(null, "输入图书编号不存在", "借书失败", JOptionPane.ERROR_MESSAGE);
                tf_number.setText("");
                tf_dateOff.setText("");
            } else {
                // 检查状态 (只传入 bookId)
                // 注意：这里需要确保数据库里 state 默认是 "in"，如果是 NULL 可能需要 sqlConn 处理一下，或者这里判断 "null"
                String currentState = sqlConn.search_bookState(bookId);

                // 假设数据库默认 insert 时 state 是 "in" (对应之前代码)
                // 或者 state 是 "0" (对应更早的 SQL)。
                // 你的代码之前逻辑是 insert state, 之前的 SQL 脚本也设为了 'in'
                if(currentState.equals("in") || currentState.equals("0")) {
                    // 借书操作
                    sqlConn.borrowBook_Update(bookId, Basic_Information.user, dateOff);
                    // sqlConn.borrowBook_Insert(...); // 已删除

                    tf_dateOff.setText("");
                    tf_number.setText("");
                    JOptionPane.showMessageDialog(null,"借书成功");
                    MainInterface.BorrowInfo_to_Borrow();
                    MainInterface.Borrow_to_User();
                } else {
                    // 书被借出
                    int holder = sqlConn.search_bookDateOff(bookId); // 之前方法返回 int, 如果是 date string 可能会报错，建议 sqlConn 改为返回 String
                    // 如果 sqlConn 没改返回类型，这里先保持原样调用
                    JOptionPane.showMessageDialog(null,"该书不可借，状态：" + currentState, "借书失败", JOptionPane.ERROR_MESSAGE);
                    tf_number.setText("");
                    tf_dateOff.setText("");
                }
            }
        }
    }
}