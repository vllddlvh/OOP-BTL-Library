package model.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import model.DatabaseConnector;
import model.entity.Member;
import model.entity.User;

/**
 *
 * @author Littl
 */
public class UserDAO {
    /**
     *<p>
     * Thực hiện login vào tài khoản thông qua ID và mật khẩu.
     * Kết quả trả về là User.loginAlert.
     *</p>
     *<p>
     * Hàm chỉ trả về kết quả thành công hay không.
     * Việc getInfo cho currentUser do view thực hiện lại.
     * </p>
     * 
     * @param userID = ID người dùng.
     * @param password = mật khẩu xác nhận.
     * 
     * @return  FIRST_TIMES_LOGIN, WRONG_PASSWORD, CORRECT_PASSWORD
     * 
     * @throws SQLException 
     */
    public static User.LoginAlert login(String userID, String password) throws SQLException {
        
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call login(?, ?) }");
        
        finder.setString(1, userID);
        finder.setString(2, password);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        /**
         * There are some case may happen.
         * if (role == null) thì người dùng không tồn tại hoặc sai mật khẩu.
         * else if (firstTry == true) thì là lần đăng nhập đầu tiên.
         * else không phải lần đầu thôi chứ vấn đúng.
         */
        
        while(rs.next()) {
            String role = rs.getString(1);
            if (role == null) {
                return User.LoginAlert.WRONG_PASSWORD;
            } else if (rs.getBoolean(2) == true) {
                if (role.equals("Staff")) {
                    return User.LoginAlert.CORRECT_PASSWORD_AS_STAFF;
                } else {
                    return User.LoginAlert.CORRECT_PASSWORD_AS_MEMBER;
                }
            } else {
                if (role.equals("Staff")) {
                    return User.LoginAlert.CORRECT_PASSWORD_AS_STAFF;
                } else {
                    return User.LoginAlert.CORRECT_PASSWORD_AS_MEMBER;
                }
            }
        }
        return User.LoginAlert.WRONG_PASSWORD;
    }
    
    
    /**
     * Cho phép tự cài lại mật khẩu cho một user.
     * 
     * @param userID = ID user cần đổi mật khẩu.
     * @param oldPassword = mật khẩu cũ
     * @param newPassword = mật khẩu mới
     * 
     * @return true/false
     */
    public static boolean changePassword(String userID, String oldPassword, String newPassword) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call changePassword(?, ?, ?) }");
        
        finder.setString(1, userID);
        finder.setString(2, oldPassword);
        finder.setString(3, newPassword);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while(rs.next()) {
            if (rs.getInt(1) == 1) return true;
            else return false;
        }
        
        return false;
    }
    
    /**
     * Xóa tài khoản của một người dùng (thường là Member).
     * 
     * @param ID = ID người bị xóa.
     * 
     * @throws SQLException 
     */
    public static void deleteUser(String ID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call deleteUser(?, ?) }");
        
        finder.setString(1, ID);
        finder.setString(2, "S001");
                
        finder.executeQuery();
    }
    
    /**
     * Lấy danh sách toàn bộ Member trong database.
     * 
     * @return danh sách các member.
     * 
     * @throws SQLException 
     */
    public static LinkedList<Member> getAllMember() throws SQLException {
        String sql = "SELECT * FROM library_2nd_edition.member";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        LinkedList<Member> list = new LinkedList<>();
        while(rs.next()) {
            
            Member member = new Member(rs.getString(1),
                                  rs.getString(2),
                                   rs.getString(3),
                                    rs.getString(4),
                                 rs.getString(5));
            
            list.add(member);
        }
        
        ps.close();
        rs.close();
        return list;
    }
    
    /**
     * take full information for a reading member.
     * 
     * @param ID = that member ID.
     * 
     * @return an model.Member object contain information.
     * return null if there's no such userID
     * 
     * @throws SQLException 
     */
    public static Member getMemberInfo(String ID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call getMemberInfo(?) }");
        
        finder.setString(1, ID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        String[] date;
        while (rs.next()) {
            return new Member(rs.getString(1),
                         rs.getString(2),
                          rs.getString(3),
                           rs.getString(4),
                        rs.getString(5));
        }
        return null;
    }
    
    /**
     * Add a new Member to database.
     * 
     * @param newMember = that new Member.
     * 
     * @return false if duplicate userID.
     * 
     * @throws SQLException 
     */
    public static boolean addNewMember (Member newMember) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call addMember(?, ?, ?, ?, ?) }");
        
        finder.setString(1, newMember.getID());
        finder.setString(2, newMember.getFirstName());
        finder.setString(3, newMember.getLastName());
        finder.setString(4, newMember.getContact());
        finder.setString(5, newMember.getDateOfBirth());
                
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            return rs.getBoolean(1);
        }
        return false;
    }
    
    /**
     * Sửa thông tin của thành viên (member).
     * 
     * @param member = kết quả cần dạt sau khi sửa.
     * 
     * @return true nếu sửa thành công.
     * 
     * @throws SQLException 
     */
    public static boolean updateMember(Member member) throws SQLException {
        // Câu lệnh SQL UPDATE để cập nhật thông tin
        String sql = "UPDATE library_2nd_edition.member SET firstName = ?, lastName = ?, contact = ?, dateOfBirth = ? WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
    
        // Gán giá trị cho từng tham số
        ps.setString(1, member.getFirstName());
        ps.setString(2, member.getLastName());
        ps.setString(3, member.getContact());
        ps.setString(4, member.getDateOfBirth());
        ps.setString(5, member.getID()); // ID để xác định thành viên cần cập nhật

        // Thực thi câu lệnh và kiểm tra xem có bao nhiêu dòng bị ảnh hưởng
        int rowsUpdated = ps.executeUpdate();
        ps.close();
    
        return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
    }
}
