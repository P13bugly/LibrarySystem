package org.LibrarySystem.Manager;

import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class State_Output extends JPanel implements ActionListener {
    private JButton back;
    private static JTable bookTable;             //表格
    private static DefaultTableModel tableModel; // 表格的数据模型

    public State_Output() {
        // 充满整个窗口
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(240, 255, 240));

        //顶部操作栏
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false); // 透明

        back = new JButton("返回");
        back.setFont(new Font("宋体", Font.PLAIN, 20));
        back.setPreferredSize(new Dimension(100, 40));
        back.addActionListener(this);
        topPanel.add(back);

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

        //设置表头样式
        bookTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));
        bookTable.getTableHeader().setBackground(new Color(220, 220, 220));
        bookTable.getTableHeader().setReorderingAllowed(false); // 禁止拖动列顺序

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //渲染器
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);   //水平格居中
        bookTable.setDefaultRenderer(Object.class, centerRenderer);

        //滚动
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // 左右留点边距

        add(scrollPane, BorderLayout.CENTER);
    }


    public static void setTextTable() {
        //清空表格数据
        tableModel.setRowCount(0);

        if (Basic_Information.bookArray != null) {
            for (int i = 0; i < Basic_Information.bookArray.size(); i++) {
                var book = Basic_Information.bookArray.get(i);
                // 把对象的属性直接放进数组，自动对齐
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
        if(e.getSource() == back) {
            MainInterface.StateInfo_to_State();
        }
    }
}