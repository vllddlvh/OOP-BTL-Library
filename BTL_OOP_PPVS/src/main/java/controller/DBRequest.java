package controller;

import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import model.Request;


/**
 *
 * @author Littl
 */
public class DBRequest extends DatabaseConnector {
    public static boolean borrowDocument(String userID, String documentID, int borrowQuantity) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call borrowDocument(?, ?, ?) }");
        
        finder.setString(1, userID);
        finder.setString(2, documentID);
        finder.setInt(3, borrowQuantity);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            return rs.getBoolean("Result");
        }
        return false;
    }
    
    public static boolean returnDocument(String userID, String requestID) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call returnDocument(?, ?) }");
        
        finder.setString(1, requestID);
        finder.setString(2, userID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            return rs.getBoolean("Result");
        }
        return false;
    }
    
    public static ArrayList<Request> checkForUnreturn_DocumentList(String userID) throws SQLException {
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call checkForUnreturn_BookList(?) }");
        
        finder.setString(1, userID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        ArrayList<Request> req = new ArrayList<>();
        while(rs.next()) {
            req.add(new Request(rs.getString(1), 
                                        userID, 
                               rs.getString(2),
                            rs.getInt(3), 
                               rs.getString(4), 
                               null));
        }
        
        return req;
    }
    
    public static ArrayList<Request> checkForUnreturn_MemberList(String documentID) throws SQLException {
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call checkForUnreturn_StudentList(?) }");
        
        finder.setString(1, documentID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        ArrayList<Request> req = new ArrayList<>();
        while(rs.next()) {
            req.add(new Request(rs.getString(1), 
                                  rs.getString(2), 
                                        documentID,
                            rs.getInt(3), 
                               rs.getString(4), 
                               null));
        }
        
        return req;
    }
    
    public static ArrayList<Request> getAllBorrowHistory_DocumentList(String userID) throws SQLException {
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call checkForHistory_DocumentList(?) }");
        
        finder.setString(1, userID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        ArrayList<Request> req = new ArrayList<>();
        while(rs.next()) {
            req.add(new Request(rs.getString(1), 
                                        userID, 
                               rs.getString(2),
                            rs.getInt(3), 
                               rs.getString(4), 
                               rs.getString(5)));
        }
        
        return req;
    }
}
