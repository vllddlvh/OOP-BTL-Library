/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView.TableRow;
import model.Member;
import service.MemberService;
import service.MemberServiceImpl;
import utility.ClassTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyMemberController {
    
    private JPanel jpnView;
    private JButton jbtAdd;
    private JTextField jtfSearch;
    
    private MemberService  memberService = null;
    
    private String[] listColumn = {"ID", "firstName", "lastName","contact","dateOfBirth"};

    private TableRowSorter<TableModel> rowSorter = null;
    
    public QuanLyMemberController(JPanel jpnView, JButton jbtAdd, JTextField jtfSearch) {
        this.jpnView = jpnView;
        this.jbtAdd = jbtAdd;
        this.jtfSearch = jtfSearch;
        
        this.memberService = new MemberServiceImpl();
    }
    
    public void setDateToTable() {
        List<Member> listItem = memberService.getList();
        
        DefaultTableModel model = new ClassTableModel().setTableMember(listItem, listColumn);
        JTable table = new JTable(model);
        
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
             String text = jtfSearch.getText();
             if(text.trim().length() == 0) {
                 rowSorter.setRowFilter(null);
             } else {
                 rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                 
             }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if(text.trim().length() == 0) {
                 rowSorter.setRowFilter(null);
                } else {
                 rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                 
             }
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        }); 
        
        
        table.getTableHeader().setFont(new Font("Arrial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100,50));
        table.getTableHeader().setBackground(new Color(80,141,78));
        table.setRowHeight(40);
        table.validate();
        table.repaint();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);
        scrollPane.setPreferredSize(new Dimension(700,400));
        
        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scrollPane);
        jpnView.validate();
        jpnView.repaint();
    }  
}
