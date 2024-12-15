package model.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DatabaseConnector;
import model.entity.Staff;

/**
 *
 * @author Littl
 */
public class StaffDAO extends UserDAO {
    
    /**
     * Lấy thông tin của một Quản lý (Staff) dựa theo UserID của người đó.
     * Nếu ID nhập bị sai bởi vì đó là Member, kết quả sẽ là null.
     * 
     * @param ID = UserID của người quản lý cần tìm
     * 
     * @return một đối tượng Staff chứa thông tin của người Quản lý này.
     * 
     * @throws SQLException 
     */
    public static Staff getStaffInfo(String ID)throws SQLException {
        
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call getStaffInfo(?) }");
        
        finder.setString(1, ID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            return new Staff(rs.getString(1),
                        rs.getString(2),
                         rs.getString(3),
                          rs.getString(4),
                         rs.getString(5),
                         rs.getString(6));
        }
        return null;
    }
    
    /**
     * Thêm một staff vào trong database.
     * 
     * @param newStaff = thông tin về staff mới này.
     * @param reportTo = ID nhân viên phụ trách quản lý nhân viên mới.
     * 
     * @return false chỉ khi sai thông tin reportTo, hoặc trùng lặp ID newStaff.
     * 
     * @throws SQLException 
     */
    public static boolean addNewStaff (Staff newStaff, String reportTo) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call addStaff(?, ?, ?, ?, ?, ?) }");
        
        finder.setString(1, newStaff.getID());
        finder.setString(2, newStaff.getFirstName());
        finder.setString(3, newStaff.getLastName());
        finder.setString(4, newStaff.getContact());
        finder.setString(5, newStaff.getJobTitle());
        finder.setString(6, reportTo);
                
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            return rs.getBoolean(1);
        }
       
        return false;
    }
    
    /**
     * Chỉnh sửa thông tin nhân viên quản lý này.
     * 
     * @param staff = mẫu kết quả sau khi chỉnh sửa.
     * 
     * @return true nếu update thành công.
     * Sai ID (ID không tồn tại) sẽ trả về false.
     * 
     * @throws SQLException với hầu hết các sai sót khác
     */
    public static boolean updateStaff(Staff staff) throws SQLException {
        // Câu lệnh SQL UPDATE để cập nhật thông tin
        String sql = "UPDATE library_2nd_edition.staff SET firstName = ?, lastName = ?, contact = ?, jobTitle = ? WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
    
        // Gán giá trị cho từng tham số
        ps.setString(1, staff.getFirstName());
        ps.setString(2, staff.getLastName());
        ps.setString(3, staff.getContact());
        ps.setString(4, staff.getJobTitle());
        ps.setString(5, staff.getID()); // ID để xác định thành viên cần cập nhật

        // Thực thi câu lệnh và kiểm tra xem có bao nhiêu dòng bị ảnh hưởng
        int rowsUpdated = ps.executeUpdate();
        ps.close();
    
        return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
    }
}
