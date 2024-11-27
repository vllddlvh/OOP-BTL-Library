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

import view.SuaThongTinThanhVienJFrame;
import model.entity.Member;
import model.dao.UserDAO;

/**
 *
 * @author ADMIN
 */
public class UpdateMemberTable extends UpdateTable<Member> {
    
    private static UpdateMemberTable singleTon = new UpdateMemberTable();
    
    private UpdateMemberTable() {}
    
    /**
     * Duy trì một UpdateMemberTable duy nhất.
     * Đông thời khi thêm thành viên, ThemThanhVienJFrame gọi thẳng được cái này.
     * Nên sẽ add thêm dòng vào bảng được luôn.
     * 
     * @return singleTon duy nhất tồn tại
     */
    public static UpdateMemberTable getUpdateMemberTable() {
        return singleTon;
    }
    
    /**
     * 
     * 
     * @throws SQLException 
     */
    @Override
    public List<Member> getListElement() throws SQLException {
        allElement = UserDAO.getAllMember();
        return allElement;
    }
    
    /**
     * Thêm một thành viên vào bảng, và cả database.
     * 
     * @param newMember = thành viên mới.
     * 
     * @return true nếu không bị trùng ID.
     * 
     * @throws SQLException 
     */
    @Override
    public boolean addElement(Member newMember) throws SQLException {
        if (UserDAO.addNewMember(newMember)) {
            allElement.add(newMember);
            addRow(newMember);
            return true;
        }
        
        return false;
    }
    
    /**
     * Thêm một thành viên vào bảng ở view.
     * 
     * @param member = thành viên được thêm vào.
     */
    @Override
    protected void addRow(Member member) {
        String[] newLine = new String[5]; // Số lượng cột trong listColumn
        newLine[0] = member.getID();
        newLine[1] = member.getFirstName();
        newLine[2] = member.getLastName();
        newLine[3] = member.getContact();
        newLine[4] = member.getDateOfBirth(); // Hiển thị ngày sinh
            
        tableModel.addRow(newLine);
    }
    
    /**
     * Chỉnh sửa thông tin của một thành viên. 
     * Cả ở database và view.
     * 
     * @param alter = thằng bị sửa.
     *  
     * @return true nếu thay đổi thành công.
     * Không thì sẽ báo lỗi, hoặc false nếu sai ID.
     * 
     * @throws SQLException 
     */
    @Override
    public boolean updateElement(Member alter) throws SQLException {
        if (UserDAO.updateMember(alter)) {
            int i = 0;
            while (i < allElement.size()) {
                if (allElement.get(i).getID().equals(alter.getID())) {
                    allElement.set(i, new Member(alter));
                    
                    break;
                }
            }
            
            updateRow(alter);
            return true;
        }
        return false;
    }
    
    /**
     * Chỉnh sửa thông tin của một hàng tại view.
     * 
     * @param alter = thành viên phải sửa thông tin.
     */
    @Override 
    protected void updateRow(Member alter) {
        int n = tableModel.getRowCount();
        for (int i = 0; i < n; i++) {
            if (tableModel.getValueAt(i, 0).equals(alter.getID())) {
                tableModel.setValueAt(alter.getFirstName(), i, 1);
                tableModel.setValueAt(alter.getLastName(), i, 2);
                tableModel.setValueAt(alter.getContact(), i, 3);
                tableModel.setValueAt(alter.getDateOfBirth(), i, 4);
                
                break;
            }
        }
    }
    
    /**
     * Thực hiện xóa một Member ra khỏi database.
     * 
     * @param deletedOne = thằng bị xóa
     * 
     * @return true khi xóa xong. Không thì sẽ ném lỗi.
     * 
     * @throws SQLException 
     */
    @Override
    public boolean deleteElement(Member deletedOne) throws SQLException {
        UserDAO.deleteUser(deletedOne.getID());
                
        int n = allElement.size();
        for (int i = 0; i < n; i++) {
            if (allElement.get(i).getID().equals(deletedOne.getID())) {
                allElement.remove(i);
                
                break;
            }
        }
            
        deleteRow(deletedOne);
        return true;
    }
    
    /**
     * Xóa hàng ở trong bảng ở view.
     * 
     * @param deletedOne = thằng bị xóa.
     */
    @Override 
    protected void deleteRow(Member deletedOne) {
        int n = tableModel.getRowCount();
        for (int i = 0; i < n; i++) {
            if (tableModel.getValueAt(i, 0).equals(deletedOne.getID())) {
                tableModel.removeRow(i);
                
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
        for (Member member : allElement) {
            addRow(member);
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
                    int row = table.getSelectedRow();
                    // Lấy thông tin từ hàng được chọn
                    Member mem = null;
                    String targetID = table.getValueAt(row, 0).toString();
                    for (Member x : allElement) {
                        if (x.getID().equals(targetID)) {
                            mem = x;
                            break;
                        }
                    }
                    
                    // Mở form chỉnh sửa tại đây (nếu có)
                    if (mem != null) {
                        new SuaThongTinThanhVienJFrame(mem).setVisible(true);
                    }
                }
            }
        });
    }
    
    private void updateFilter(JTextField jtfSearch, TableRowSorter<TableModel> rowSorter) {
        String text = jtfSearch.getText();
        if(text.trim().equalsIgnoreCase("Tìm kiếm thông tin thành viên") || text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
    
}
