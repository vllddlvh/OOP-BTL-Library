package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class UpdateTableMuonTraTaiLieu extends UpdateTable<Request> {
    public static final int MY_HISTORY = 0;
    public static final int ALL_SYSTEM = 1;
    
    private static final UpdateTableMuonTraTaiLieu singleTon = new UpdateTableMuonTraTaiLieu();

    private UpdateTableMuonTraTaiLieu() {
    }
    
    public static void initTable(int optionList) {
        try {
            switch (optionList) {
                case MY_HISTORY : 
                    singleTon.allElement = LoginController.getAcc().getMyBorrowHistory();
                    break;
                case ALL_SYSTEM :
                    if (LoginController.getAcc() instanceof model.entity.Staff) {
                        singleTon.allElement = RequestDAO.getAllRequest();
                    } else {
                        singleTon.allElement = new ArrayList<>();
                    }
                    break;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateTableMuonTraTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static UpdateTableMuonTraTaiLieu getInstance() {
        return singleTon;
    }

    @Override
    public ArrayList<Request> getListElement()  {
        return allElement;
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
        model.dao.RequestDAO.updateDateRequest(updatedRequest);
        
        String target = updatedRequest.getRequestID();
        for (int i = allElement.size() - 1; i >= 0; i--) {
            Request x = allElement.get(i);
            if (x.getRequestID().equals(target)) {
                allElement.set(i, updatedRequest);
                break;
            }
        }
        
        updateRow(updatedRequest);
        return true;
    }

    
    
    @Override
    public void updateRow(Request updatedRequest) {
        int n = tableModel.getRowCount();
        String reqID = updatedRequest.getRequestID();
        for (int i = 0; i < n; i++) {
            if (reqID.equals(tableModel.getValueAt(i, 0))) {
                tableModel.setValueAt(updatedRequest.getBorrowDate(), i, 5);
                tableModel.setValueAt(updatedRequest.getReturnDate(), i, 6);
                
                break;
            }
        }
    }

    
    
    @Override
    public void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException {
        singleTon.tableModel = (DefaultTableModel) table.getModel();
        singleTon.jbtAdd = jbtAdd;
        singleTon.jtfSearch = jtfSearch;
        
        // addAll dòng mới vào trong tableModel
        rewriteTable();
        
        
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
                int selectedRow = table.getSelectedRow();
                if (e.getClickCount() >= 1 && selectedRow != -1) {
                    String targetID = (String) table.getValueAt(selectedRow, 0);
                    Request req = null;
                    for (Request x : allElement) {
                        if (x.getRequestID().equals(targetID)) {
                            req = x;
                        }
                    }
                    
                    if (req != null) {
                        new view.MuonSachJFrame(req).setVisible(true);
                        tableModel.setValueAt(req.getBorrowDate(), selectedRow, 5);
                        tableModel.setValueAt(req.getReturnDate(), selectedRow, 6);
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
    
    public static void unreturnFilter() {
        int i = singleTon.tableModel.getRowCount() - 1;
        for (; i >= 0; i--) {
            String s = (String) singleTon.tableModel.getValueAt(i, 6);
            if (s == null || s.length() == 0) {
                continue;
            }
            singleTon.tableModel.removeRow(i);
        }
    }
    
    public static void rewriteTable() {
        int i = singleTon.tableModel.getRowCount() - 1;
        for (; i >= 0; i--) {
            singleTon.tableModel.removeRow(i);
        }
        for (Request req : singleTon.allElement) {
            singleTon.addRow(req);
        }
    }
    
    public void returnRequest(Request req) throws SQLException {
        // Nếu Staff tự trả của bản thân, thì thông tin cần được update ngay lên trang chủ.
        if (req.getUserID().equals(LoginController.getAcc().getID())) {
            LoginController.returnDocument(req.getDocumentID());
        } else {
            RequestDAO.returnDocument(req.getUserID(), req.getDocumentID());
        }
        
        req = RequestDAO.getRequest(req.getRequestID());
        updateElement(req);
    }
    
    
    
    // Đoạn này không dùng. Do không tự dưng add phiếu mượn được, hay xóa đi được.
    // Ảnh hưởng nhiều vấn đề lắm.
    @Override
    public void deleteRow(Request deleteRequest) {
    }

    @Override
    protected void addRow(Request request) {
        String[] newRow = new String[7];
        newRow[0] = request.getRequestID();
        newRow[1] = request.getUserID();
        newRow[2] = request.getUser_fullName();
        newRow[3] = request.getDocumentID();
        newRow[4] = request.getDocument_title();
        newRow[5] = request.getBorrowDate();
        newRow[6] = request.getReturnDate();
        
        tableModel.addRow(newRow);
    }
  
    @Override
    public boolean addElement(Request request) throws SQLException {
        return false;
    }
    
    @Override
    public boolean deleteElement(Request deleteRequest) throws SQLException {
        return false;
    }
}
