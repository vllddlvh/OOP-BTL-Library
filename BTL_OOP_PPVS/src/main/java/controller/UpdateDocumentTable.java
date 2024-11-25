/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import view.SuaThongTinTaiLieuSachFrame;
import model.entity.Document;
import model.dao.DocumentDAO;

/**
 * Quản lý cập nhật bảng Document (JTable) đồng bộ với dữ liệu trong Database.
 */
public class UpdateDocumentTable extends UpdateTable<Document> {

    private static UpdateDocumentTable singleton = new UpdateDocumentTable();

    private UpdateDocumentTable() {}

    /**
     * Singleton: Lấy instance duy nhất của UpdateDocumentTable.
     * 
     * @return instance của UpdateDocumentTable
     */
    public static UpdateDocumentTable getUpdateDocumentTable() {
        return singleton;
    }

    /**
     * Lấy danh sách Document từ cơ sở dữ liệu.
     */
    @Override
    protected void getListElement() throws SQLException {
        allElement = DocumentDAO.getAllDocuments();
    }

    /**
     * Thêm một Document vào cơ sở dữ liệu và cập nhật bảng.
     * @param newDocument.
     * @throws java.sql.SQLException
     */
    @Override
    public boolean addElement(Document newDocument) throws SQLException {
        if (DocumentDAO.addNewDocument(newDocument)) {
            allElement.add(newDocument);
            addRow(newDocument);
            return true;
        }
        return false;
    }

    /**
     * Cập nhật thông tin một Document trong cơ sở dữ liệu và bảng.
     */
    @Override
    public boolean updateElement(Document updatedDocument) throws SQLException {
        if (DocumentDAO.updateDocument(updatedDocument)) {
            for (int i = 0; i < allElement.size(); i++) {
                if (allElement.get(i).getID().equals(updatedDocument.getID())) {
                    allElement.set(i, updatedDocument); // Cập nhật danh sách
                    break;
                }
            }
            updateRow(updatedDocument); // Cập nhật hiển thị trên bảng
            return true;
        }
        return false;
    }

    /**
     * Xóa một Document khỏi cơ sở dữ liệu và bảng.
     */
    @Override
    public boolean deleteElement(Document deleteDocument) throws SQLException {
        
        if (DocumentDAO.deleteDocument(deleteDocument)) {
            // Xóa khỏi danh sách
            for (int i = 0; i < allElement.size(); i++) {
                if (allElement.get(i).getID().equals(deleteDocument.getID())) {
                    allElement.remove(i);
                    break;
                }
            }
            // Xóa khỏi bảng hiển thị
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).toString().equals(deleteDocument.getID())) {
                    tableModel.removeRow(i);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Cập nhật một hàng (row) trong bảng hiển thị (JTable).
     */
    @Override
    public void updateRow(Document updatedDocument) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 0).equals(updatedDocument.getID())) {
                tableModel.setValueAt(updatedDocument.getTitle(), row, 1);
                tableModel.setValueAt(updatedDocument.getAuthor(), row, 2);
                tableModel.setValueAt(updatedDocument.getPublicationYear(), row, 3);
                tableModel.setValueAt(updatedDocument.getCategory(), row, 4);
                tableModel.setValueAt(updatedDocument.getLanguage(), row, 5);
                break;
            }
        }
    }

    /**
     * Thêm một hàng (row) mới vào bảng hiển thị (JTable).
     */
    @Override
    protected void addRow(Document document) {
        String[] newRow = new String[6];
        newRow[0] = document.getID();
        newRow[1] = document.getTitle();
        newRow[2] = document.getAuthor();
        newRow[3] = document.getPublicationYear();
        newRow[4] = document.getCategory();
        newRow[5] = document.getLanguage();

        tableModel.addRow(newRow);
    }

    /**
     * Thiết lập và cập nhật dữ liệu cho bảng (JTable).
     */
    @Override
    public void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException {
        singleton.tableModel = (DefaultTableModel) table.getModel();
        singleton.jbtAdd = jbtAdd;
        singleton.jtfSearch = jtfSearch;

        // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào bảng
        getListElement();
        for (Document document : allElement) {
            addRow(document);
        }

        // Thiết lập bộ lọc tìm kiếm
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter(jtfSearch, rowSorter);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter(jtfSearch, rowSorter);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        // Xử lý sự kiện nhấp chuột
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    // Lấy thông tin từ hàng được chọn
                    String id = table.getValueAt(row, 0).toString();
                    String title = table.getValueAt(row, 1).toString();
                    String author = table.getValueAt(row, 2).toString();
                    String year = table.getValueAt(row, 3).toString();
                    String category = table.getValueAt(row, 4).toString();
                    String language = table.getValueAt(row, 5).toString();
                    // Mở form chỉnh sửa tại đây (nếu có)
                    new SuaThongTinTaiLieuSachFrame(id, title, author, year, category, language).setVisible(true);
                }
            }
        });
    }

    
    private void updateFilter(JTextField jtfSearch, TableRowSorter<TableModel> rowSorter) {
        String text = jtfSearch.getText();
        if (text.trim().isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
}
