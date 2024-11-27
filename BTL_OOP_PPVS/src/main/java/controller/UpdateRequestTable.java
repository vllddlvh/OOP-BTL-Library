package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.dao.RequestDAO;
import model.entity.Request;

public class UpdateRequestTable extends UpdateTable<Request> {
    private static final UpdateRequestTable singleTon = new UpdateRequestTable();

    private UpdateRequestTable() {}

    public static UpdateRequestTable getUpdateRequestTable() {
        return singleTon;
    }

    @Override
    public LinkedList<Request> getListElement() throws SQLException {
        allElement = RequestDAO.getAllRequest();
        return allElement;
    }

    /**
     * Muon sach.
     * 
     * @param request thong tin.
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addElement(Request request) throws SQLException {
        if(RequestDAO.borrowDocument(request.getUserID(), request.getDocumentID())) {
            allElement.add(request);
            addRow(request);
            return true;
        }
        return false;
    }

    /**
     * Tra yêu cầu
     * 
     * @param updatedRequest yêu cầu mượn trả duoc tra.
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateElement(Request updatedRequest) throws SQLException {
        updateRow(updatedRequest);
        return true;
    }

    @Override
    public boolean deleteElement(Request deleteRequest) throws SQLException {
        return false;
    }
    
    @Override
    public void deleteRow(Request deleteRequest) {
        
    }

    @Override
    protected void addRow(Request request) {
        String[] newLine = new String[6]; // Số lượng cột trong listColumn
        newLine[0] = request.getRequestID();
        newLine[1] = request.getUserID();
        newLine[2] = request.getDocumentID();
        newLine[3] = String.valueOf(request.getQuantityBorrow());
        newLine[4] = request.getBorrowDate();
        newLine[5] = request.getReturnDate();
            
        tableModel.addRow(newLine);
    }
    
    @Override
    public void updateRow(Request updatedRequest) {
        int n = tableModel.getRowCount();
        String reqID = updatedRequest.getRequestID();
        for (int i = 0; i < n; i++) {
            if (reqID.equals(tableModel.getValueAt(i, 0))) {
                tableModel.setValueAt(updatedRequest.getUserID(), i, 1);
                tableModel.setValueAt(updatedRequest.getDocumentID(), i, 2);
                tableModel.setValueAt(updatedRequest.getQuantityBorrow(), i, 3);
                tableModel.setValueAt(updatedRequest.getBorrowDate(), i, 4);
                tableModel.setValueAt(updatedRequest.getReturnDate(), i, 5);
                
                break;
            }
        }
    }

    
    
    @Override
    public void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException {
        singleTon.tableModel = (DefaultTableModel) table.getModel();
        singleTon.jbtAdd = jbtAdd;
        singleTon.jtfSearch = jtfSearch;
        
        // Lấy toàn bộ thông tin từ DB. 
        // addAll dòng mới vào trong tableModel
        getListElement();
        for (Request req : allElement) {
            addRow(req);
        }
        
        
        // Setting để có chức năng sort ngay trên table
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        
        // ActionListener cho thanh search (J Text Field)
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
            public void changedUpdate(DocumentEvent e) {
                System.out.println("Track changed Update");
            }
        }); 
        table.addMouseListener(new MouseAdapter(){
            @Override 
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    String targetID = (String) table.getValueAt(table.getSelectedRow(), 0);
                    Request req = null;
                    for (Request x : allElement) {
                        if (x.getRequestID().equals(targetID)) {
                            req = x;
                        }
                    }
                    
                    if (req != null) {
                        // Chỗ này cần: new SuaYeuCauMuonTraSach(req).setVisible(true)
                        JOptionPane.showMessageDialog(null, "Chức năng Sửa Yêu Cầu Mượn/Trả đang phát triển!");
                    }
                }
            }
        });
    }
    
    
    private void updateFilter(JTextField jtfSearch, TableRowSorter<TableModel> rowSorter) {
        String text = jtfSearch.getText();
        if(text.trim().equalsIgnoreCase("Tìm kiếm thông tin Yêu cầu Mượn/Trả tài liệu") || text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
}
