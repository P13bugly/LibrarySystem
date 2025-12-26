package org.LibrarySystem.User;

import org.LibrarySystem.Book;
import org.LibrarySystem.DataBase.sqlConn;
import org.LibrarySystem.Main_UI.MainInterface;
import org.LibrarySystem.Static.Basic_Information;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalInfo extends JPanel implements ActionListener {
    private JButton back;
    private static JTable infoTable;
    private static DefaultTableModel tableModel;

    public PersonalInfo(){
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(127, 255, 212));

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

        JLabel titleLabel = new JLabel("个人信息", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(150, 50));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {
                "图书编号", "分类名称", "图书名称", "还书期限"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        infoTable = new JTable(tableModel);
        infoTable.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        infoTable.setRowHeight(30);

        infoTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 18));
        infoTable.getTableHeader().setBackground(new Color(230, 230, 230));
        infoTable.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        infoTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(infoTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        add(scrollPane, BorderLayout.CENTER);

        this.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                System.out.println("面板可见，正在刷新数据... 当前用户: " + Basic_Information.user);
                initData();
            }
            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {}
            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent event) {}
        });
    }

    private void initData(){
        sqlConn.search_user(Basic_Information.user);
        tableModel.setRowCount(0);

        for(Book book :Basic_Information.bookArray){
            Object [] Rowdata ={
                    book.number,
                    book.className,
                    book.name,
                    book.dateOff
            };
            tableModel.addRow(Rowdata);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back){
            MainInterface.Personal_to_User();
        }
    }
}