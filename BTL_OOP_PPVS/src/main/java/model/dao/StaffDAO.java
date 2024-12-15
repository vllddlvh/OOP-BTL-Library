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
     * take full information for a library Staff.
     * 
     * @param ID = that staff ID.
     * 
     * @return an model.Staff object contain information.
     * return null if there's no such userID
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
     * Add a new Staff to database.
     * 
     * @param newStaff = that new comer.
     * @param reportTo = who adding this staff.
     * 
     * @return false if duplicate userID.
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
