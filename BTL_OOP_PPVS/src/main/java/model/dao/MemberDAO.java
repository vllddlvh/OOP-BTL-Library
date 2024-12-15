package model.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import model.DatabaseConnector;
import model.entity.Member;

/**
 *
 * @author Littl
 */
public class MemberDAO {
    
    /**
     * Lấy thông tin toàn bộ Người Dùng Phổ Thông (Member).
     * 
     * @return List Member hiện có trong database.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Member> getAllMember() throws SQLException {
        String sql = "SELECT * FROM library_2nd_edition.member";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Member> list = new ArrayList<>();
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
     * Lấy thông tin Member lẻ, dựa theo UserID.
     * Thường là để khi đăng nhập.
     * 
     * @param ID = userID cần tìm.
     * 
     * @return null nếu không có userID này trong hệ thống.
     * 
     * @throws SQLException 
     */
    public static Member getMemberInfo(String ID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call getMemberInfo(?) }");
        
        finder.setString(1, ID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
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
     * Thêm member mới. 
     * 
     * @param newMember = that new Member.
     * 
     * @return false nếu trùng lặp ID người dùng. Có thể trùng với Staff hoặc Member
     * 
     * @throws SQLException 
     */
    public static boolean addNewMember (Member newMember) throws SQLException {
        CallableStatement finder = (CallableStatement) 
                DatabaseConnector.getConnection().prepareCall("{ call addMember(?, ?, ?, ?, ?) }");
        
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
     * Update (chỉnh sửa) thông tin cho Member sẵn trong database.
     * 
     * @param member = mẫu kết quả sau chỉnh sửa.
     * 
     * @return false nếu sai ID người dùng. 
     * 
     * @throws SQLException 
     */
    public static boolean updateMember(Member member) throws SQLException {
        String sql = "UPDATE library_2nd_edition.member SET firstName = ?, lastName = ?, contact = ?, dateOfBirth = ? WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
    
        ps.setString(1, member.getFirstName());
        ps.setString(2, member.getLastName());
        ps.setString(3, member.getContact());
        ps.setString(4, member.getDateOfBirth());
        ps.setString(5, member.getID()); 

        int rowsUpdated = ps.executeUpdate();
        ps.close();
    
        return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
    }
}
