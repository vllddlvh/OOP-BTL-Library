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
 * Lớp UpdateTableThongTinThanhVien giúp quản lý bảng thông tin thành viên,
 * hỗ trợ các thao tác như thêm, sửa, xóa, cập nhật bảng.
 */
public class UpdateTableThongTinThanhVien extends UpdateTable<Member> {
    
    private static UpdateTableThongTinThanhVien instance = new UpdateTableThongTinThanhVien();
    
    private UpdateTableThongTinThanhVien() {}
    
    /**
     * Duy trì một thể hiện duy nhất của UpdateTableThongTinThanhVien.
     * @return instance duy nhất của lớp.
     */
    public static UpdateTableThongTinThanhVien getInstance() {
        return instance;
    }
    
    /**
     * Lấy danh sách tất cả thành viên từ cơ sở dữ liệu.
     * @return Danh sách các đối tượng Member.
     * @throws SQLException Nếu xảy ra lỗi truy vấn cơ sở dữ liệu.
     */
    @Override
    public List<Member> getListElement() throws SQLException {
        allElement = UserDAO.getAllMember();
        return allElement;
    }
    
    /**
     * Thêm một thành viên mới vào bảng và cơ sở dữ liệu.
     * @param newMember Thành viên mới cần thêm.
     * @return true nếu thêm thành công, false nếu trùng ID.
     * @throws SQLException Nếu xảy ra lỗi truy vấn cơ sở dữ liệu.
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
     * Thêm một dòng mới vào bảng (hiển thị view).
     * @param member Thành viên cần thêm vào bảng.
     */
    @Override
    protected void addRow(Member member) {
        String[] newLine = new String[5];
        newLine[0] = member.getID();
        newLine[1] = member.getFirstName();
        newLine[2] = member.getLastName();
        newLine[3] = member.getContact();
        newLine[4] = member.getDateOfBirth();
        tableModel.addRow(newLine);
    }
    
    /**
     * Cập nhật thông tin thành viên trong cơ sở dữ liệu và bảng.
     * @param alter Thành viên cần cập nhật thông tin.
     * @return true nếu cập nhật thành công, false nếu ID không hợp lệ.
     * @throws SQLException Nếu xảy ra lỗi truy vấn cơ sở dữ liệu.
     */
    @Override
    public boolean updateElement(Member alter) throws SQLException {
        if (UserDAO.updateMember(alter)) {
            for (int i = 0; i < allElement.size(); i++) {
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
     * Cập nhật thông tin của một dòng trong bảng (view).
     * @param alter Thành viên cần cập nhật.
     */
    @Override 
    protected void updateRow(Member alter) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
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
     * Xóa một thành viên khỏi cơ sở dữ liệu và bảng.
     * @param deletedOne Thành viên cần xóa.
     * @return true nếu xóa thành công, false nếu xảy ra lỗi.
     * @throws SQLException Nếu xảy ra lỗi truy vấn cơ sở dữ liệu.
     */
    @Override
    public boolean deleteElement(Member deletedOne) throws SQLException {
        UserDAO.deleteUser(deletedOne.getID());
        for (int i = 0; i < allElement.size(); i++) {
            if (allElement.get(i).getID().equals(deletedOne.getID())) {
                allElement.remove(i);
                break;
            }
        }
        deleteRow(deletedOne);
        return true;
    }
    
    /**
     * Xóa một dòng trong bảng (view).
     * @param deletedOne Thành viên cần xóa khỏi bảng.
     */
    @Override 
    protected void deleteRow(Member deletedOne) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(deletedOne.getID())) {
                tableModel.removeRow(i);
                break;
            }
        }
    }
    
    /**
     * Cập nhật bảng hiển thị dựa trên dữ liệu mới nhất từ cơ sở dữ liệu.
     * Thiết lập các tính năng tìm kiếm và chọn thành viên.
     * @param table Bảng hiển thị thông tin.
     * @param jbtAdd Nút thêm thành viên.
     * @param jtfSearch Trường tìm kiếm thông tin.
     * @throws SQLException Nếu xảy ra lỗi truy vấn cơ sở dữ liệu.
     */
    @Override
    public void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException {
        instance.tableModel = (DefaultTableModel) table.getModel();
        instance.jbtAdd = jbtAdd;
        instance.jtfSearch = jtfSearch;
        
        getListElement();
        for (Member member : allElement) {
            addRow(member);
        }
        
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
            public void changedUpdate(DocumentEvent e) {
                // Không cần thiết, chỉ để tracking
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    String targetID = table.getValueAt(row, 0).toString();
                    Member mem = allElement.stream()
                            .filter(m -> m.getID().equals(targetID))
                            .findFirst()
                            .orElse(null);
                    if (mem != null) {
                        new SuaThongTinThanhVienJFrame(mem).setVisible(true);
                    }
                }
            }
        });
    }
    
    private void updateFilter(JTextField jtfSearch, TableRowSorter<TableModel> rowSorter) {
        String text = jtfSearch.getText();
        if (text.trim().isEmpty() || text.equalsIgnoreCase("Tìm kiếm thông tin thành viên")) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
}
