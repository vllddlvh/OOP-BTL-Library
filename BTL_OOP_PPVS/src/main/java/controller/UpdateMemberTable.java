package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
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

import model.entity.Member;
import model.dao.MemberDAO;
import view.SuaThongTinThanhVienJFrame;

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
    
    @Override
    public void getListElement() throws SQLException {
        allElement = MemberDAO.getAllMember();
    }
    
    @Override
    public boolean addElement(Member newMember) throws SQLException {
        if (MemberDAO.addNewMember(newMember)) {
            allElement.add(newMember);
            addRow(newMember);
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean updateElement(Member updatedMember) throws SQLException {
        // Cập nhật thông tin vào cơ sở dữ liệu
        if (MemberDAO.updateMember(updatedMember)) {
            // Cập nhật lại `allElement` (list lưu toàn bộ các phần tử trong bảng)
            for (int i = 0; i < allElement.size(); i++) {
                if (allElement.get(i).getID().equals(updatedMember.getID())) {
                    allElement.set(i, updatedMember); // Cập nhật thông tin trong danh sách
                    break;
                }
            }

            // Đồng bộ lại `JTable`
            updateRow(updatedMember);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean deleteElement(Member deleteMember) throws SQLException {
    // Xóa thành viên trong cơ sở dữ liệu
    if (MemberDAO.deleteMember(deleteMember)) {
        // Xóa thành viên khỏi danh sách `allElement`
        for (int i = 0; i < allElement.size(); i++) {
            if (allElement.get(i).getID().equals(deleteMember.getID())) {
                allElement.remove(i); // Xóa thành viên khỏi danh sách
                break;
            }
        }

        // Xóa dòng khỏi bảng `JTable`
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equals(deleteMember.getID())) {
                tableModel.removeRow(i); // Xóa dòng trong `JTable`
                break;
            }
        }
        return true; // Trả về thành công sau khi đã xóa
    }
    return false; // Trả về thất bại nếu không xóa được
    }

    @Override
    public void updateRow(Member updatedMember) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 0).equals(updatedMember.getID())) {
                tableModel.setValueAt(updatedMember.getFirstName(), row, 1);
                tableModel.setValueAt(updatedMember.getLastName(), row, 2);
                tableModel.setValueAt(updatedMember.getContact(), row, 3);
                tableModel.setValueAt(updatedMember.getDateOfBirth(), row, 4);
                break;
            }
        }
    }
    
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
                String text = jtfSearch.getText();
                if(text.trim().equalsIgnoreCase("Tìm kiếm thông tin thành viên") || text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if(text.trim().equalsIgnoreCase("Tìm kiếm thông tin thành viên") || text.trim().length() == 0) {
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
        table.addMouseListener(new MouseAdapter(){
            @Override 
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                     // Lấy giá trị của từng cột từ dòng được chọn
                    String id = table.getValueAt(row, 0).toString();       
                    String firstName = table.getValueAt(row, 1).toString(); 
                    String lastName = table.getValueAt(row, 2).toString();  
                    String contact = table.getValueAt(row, 3).toString();   
                    String dob = table.getValueAt(row, 4).toString();       
                    SuaThongTinThanhVienJFrame suaThongTin = new SuaThongTinThanhVienJFrame(id,firstName,lastName,contact,dob);
                    suaThongTin.setVisible(true);
                }
            }
        });
    }
    
}
