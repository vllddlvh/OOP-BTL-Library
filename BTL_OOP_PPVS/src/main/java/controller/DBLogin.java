package controller;


import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.User.loginAlert;


public class DBLogin extends DatabaseConnector {
    
    
    /**
     * Check if the password is right.
     * 
     * @param ID = user ID.
     * @param password = account password.
     * 
     * @return as loginAlert type FROM model.User
     * 
     * @throws SQLException 
     */
    public static loginAlert login(String userID, String password) throws SQLException {
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call login(?, ?) }");
        
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
                return loginAlert.WRONG_PASSWORD;
            } else if (rs.getBoolean(2) == true) {
                return loginAlert.FIRST_TIMES_LOGIN;
            } else {
                
                // must apply info to view.GD_mainStream.currentUser;
                
                return loginAlert.CORRECT_PASSWORD;
            }
        }
        return loginAlert.WRONG_PASSWORD;
    }
    
    /**
     * Provide way to change Password of an account as if the account exists in the database,
     * 
     * @param userID = ID of one who want to change password.
     * @param oldPassword = old password to confirm.
     * @param newPassword = new password.
     * 
     * @return false if wrong old-password or account not exists.
     * 
     * @throws SQLException 
     */
    public static boolean changePassword(String userID, String oldPassword, String newPassword) throws SQLException {
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call changePassword(?, ?, ?) }");
        
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
     * simple test for method above.
     * 
     * @param args = sth. 
     */
    public static void main(String[] args) {
        try {
            DatabaseConnector.firstTODO();
            System.out.println(changePassword ("U001", null, "2k5"));
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
