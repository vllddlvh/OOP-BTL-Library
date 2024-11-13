package controller;


import model.User;
import model.Staff;
import model.Member;


import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public class DBUser extends DatabaseConnector {
    
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
    public static Member getMemberInfo(String ID)throws SQLException {
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call getMemberInfo(?) }");
        
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
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call getStaffInfo(?) }");
        
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
     * @param whoManageNewbie = one who introduce/manage/senior for the new.
     * 
     * @return false if duplicate userID.
     * 
     * @throws SQLException 
     */
    public static boolean addNewStaff(Staff newStaff, Staff whoManageNewbie) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call addStaff(?, ?, ?, ?, ?, ?) }");
        
        finder.setString(1, newStaff.getID());
        finder.setString(2, newStaff.getFirstName());
        finder.setString(3, newStaff.getLastName());
        finder.setString(4, newStaff.getContact());
        finder.setString(5, newStaff.getJobTitle());
        finder.setString(6, whoManageNewbie.getID());
                
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            return rs.getBoolean(1);
        }
        return false;
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
    public static boolean addNewMember(Member newMember) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call addStudent(?, ?, ?, ?, ?, ?) }");
        
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
     * Delete an user from database.
     * 
     * @param whoBeDeleted = the user being deleted.
     * @param whoDoThis = the Staff who do the deleting job.
     * 
     * @throws SQLException 
     */
    public static void deleteUser(User whoBeDeleted, Staff whoDoThis) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call deleteUser(?, ?) }");
        
        finder.setString(1, whoBeDeleted.getID());
        finder.setString(2, whoDoThis.getID());
                
        finder.executeQuery();
    }
}
