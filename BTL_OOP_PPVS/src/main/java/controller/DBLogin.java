package controller;


import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.CallableStatement;


public class DBLogin extends DatabaseConnector {
    public final static int FIRST_LOG_IN = 2;
    public final static int TRUE_LOG_IN = 1;
    public final static int FAIL_LOG_IN = 0;
    
    
    /**
     * Check if the password is right/
     * 
     * @param ID = user ID.
     * @param password = account password.
     * 
     * @return FIRST_LOG_IN if this your first time.
     *  TRUE_LOG_IN if the password right.
     *  FAIL_LOG_IN if the password wrong.
     * 
     * @throws SQLException 
     */
    public static int logIn(String ID, String password) throws SQLException {
        ResultSet rs;
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call checkPassword(?, ?) }");
        /**
         * Procedure checkPassword:
         *      'notYet' for the first time LogIn user
         *      'noSuchID' for wrong userID
         *      '1' mean true password
         *      '0' mean wrong password.
         */
        
        finder.setString(1, ID);
        finder.setString(2, password);
        
        rs = finder.executeQuery();
        
        while(rs.next()) {
            switch (rs.getString("Result")) {
                case "NotYet" -> {
                    // firstLogIn
                    return FIRST_LOG_IN;
                }
                case "1" -> {
                    // True
                    return TRUE_LOG_IN;
                }
                default -> {
                    // WRONG PASSWORD OR USER_ID
                    return FAIL_LOG_IN;
                }
            }
        }
        return FAIL_LOG_IN;
    }
    
    
    /**
     * Create new Password for the account if this is its first time.
     * 
     * @param ID = user ID.
     * @param newPassword = new Password.
     * 
     * @return FAIL_LOG_IN if this not it first time.
     * 
     * @throws SQLException 
     */
    public static int firstLogIn(String ID, String newPassword) throws SQLException {
        ResultSet rs;
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call firstLogIn(?, ?) }");
        /**
         * Procedure firstLogIn:
         *      '1' mean it's done.
         *      '0' mean this not your first time.
         */
        
        finder.setString(1, ID);
        finder.setString(2, newPassword);
        
        rs = finder.executeQuery();
        
        while(rs.next()) {
            if (rs.getBoolean("Result") == true) {
                return TRUE_LOG_IN;
            } 
            else return FAIL_LOG_IN;
        }
        return FAIL_LOG_IN;
    }
    
    
    /**
     * Change Password for the account.
     * 
     * @param ID = user ID.
     * @param oldPassword = previous Password.
     * @param newPassword = new one.
     * 
     * @return FAIL_LOG_IN if the old password is WRONG.
     * 
     * @throws SQLException 
     */
    public static int changePassword(String ID, String oldPassword, String newPassword) throws SQLException {
        ResultSet rs;
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call changePassword(?, ?, ?) }");
        /**
         * Procedure firstLogIn:
         *      '1' mean it's done.
         *      '0' mean your oldPassword WRONG
         */
        
        finder.setString(1, ID);
        finder.setString(2, oldPassword);
        finder.setString(3, newPassword);
        
        rs = finder.executeQuery();
        
        while(rs.next()) {
            if (rs.getBoolean("Result") == true) {
                return TRUE_LOG_IN;
            }
            return FAIL_LOG_IN;
        }
        return FAIL_LOG_IN;
    }
     
}
