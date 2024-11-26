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

import model.entity.Document;
import model.dao.DocumentDAO;
import view.SuaThongTinTaiLieuSachFrame;

public class UpdateDocumentTable extends UpdateTable<Document> {

    private static final UpdateDocumentTable singleT = new UpdateDocumentTable();

    private UpdateDocumentTable() {}

    public static UpdateDocumentTable getUpdateDocumentTable() {
        return singleT;
    }

    @Override
    public void getListElement() throws SQLException {
        allElement = DocumentDAO.getAllDocuments();
    }

    
    /**
     *
     * @return
     * @throws SQLException
     */
    public List<Document> getAlldcms() throws SQLException {
        return allElement = DocumentDAO.getAllDocuments();
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

    @Override
    public boolean updateElement(Document updatedDocument) throws SQLException {
        if (DocumentDAO.updateDocument(updatedDocument)) {
            for (int i = 0; i < allElement.size(); i++) {
                if (allElement.get(i).getID().equals(updatedDocument.getID())) {
                    allElement.set(i, updatedDocument);
                    break;
                }
            }
            updateRow(updatedDocument);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteElement(Document deleteDocument) throws SQLException {
        if (DocumentDAO.deleteDocument(deleteDocument)) { // Phương thức này phải trả về true khi xóa thành công
            // Xóa khỏi danh sách `allElement` và cập nhật bảng
            for (int i = 0; i < allElement.size(); i++) {
                if (allElement.get(i).getID().equals(deleteDocument.getID())) {
                    allElement.remove(i);
                    break;
                }
            }
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).toString().equals(deleteDocument.getID())) {
                    tableModel.removeRow(i);
                    break;
                }
            }
            return true; // Trả về true khi xóa thành công
        }
        return false;
    }


    @Override
    public void updateRow(Document updatedDocument) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 0).equals(updatedDocument.getID())) {
                tableModel.setValueAt(updatedDocument.getTitle(), row, 1);
                tableModel.setValueAt(updatedDocument.getAuthor(), row, 2);
                tableModel.setValueAt(updatedDocument.getPublisher(), row, 3);
                tableModel.setValueAt(updatedDocument.getPublicationYear(), row, 4);
                tableModel.setValueAt(updatedDocument.getCategory(), row, 5);
                tableModel.setValueAt(updatedDocument.getLanguage(), row, 6);
                tableModel.setValueAt(updatedDocument.getSummary(), row, 7);
                tableModel.setValueAt(updatedDocument.getFileImage(), row, 8);
                
                break;
            }
        }
    }

    @Override
    protected void addRow(Document document) {
        String[] newLine = new String[9];
        newLine[0] = document.getID();
        newLine[1] = document.getTitle();
        newLine[2] = document.getAuthor();
        newLine[3] = document.getPublisher();
        newLine[4] = document.getPublicationYear();
        newLine[5] = document.getCategory();
        newLine[6] = document.getLanguage();
        newLine[7] = document.getSummary();
        newLine[8] = document.getFileImage();

        tableModel.addRow(newLine);
    }

    @Override
    public void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException {
        singleT.tableModel = (DefaultTableModel) table.getModel();
        singleT.jbtAdd = jbtAdd;
        singleT.jtfSearch = jtfSearch;

        getListElement();
        for (Document document : allElement) {
            addRow(document);
        }

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().equalsIgnoreCase("Tìm kiếm thông tin tài liệu") || text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().equalsIgnoreCase("Tìm kiếm thông tin tài liệu") || text.trim().isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("Track changed Update");
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    
                    String id = table.getValueAt(row, 0).toString();
                    String title = table.getValueAt(row, 1).toString();
                    String author = table.getValueAt(row, 2).toString();
                    String publisher = table.getValueAt(row,3).toString();
                    String publicationYear = table.getValueAt(row, 4).toString();
                    String category = table.getValueAt(row, 5).toString();
                    String languague = table.getValueAt(row, 6).toString();
                    String summary = table.getValueAt(row, 7).toString();
                    String fileImage = table.getValueAt(row, 8).toString();
                    SuaThongTinTaiLieuSachFrame suaThongTinTaiLieu = new SuaThongTinTaiLieuSachFrame(id, title, author, publisher, publicationYear, category, languague, summary, fileImage);
                   
                    suaThongTinTaiLieu.setVisible(true);
                }
            }
        });
    }
}
