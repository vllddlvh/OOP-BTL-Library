package model.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import model.DatabaseConnector;
import model.entity.Request;


public class RequestDAO {
    
    public static LinkedList<Request> getAllRequest() throws SQLException {
        String sql = """
                     SELECT r.*,
                          coalesce((SELECT CONCAT_WS(" ", lastName, firstName) FROM  library_2nd_edition.Member WHERE ID = r.userID),
                          (SELECT CONCAT_WS(" ", lastName, firstName) FROM  library_2nd_edition.Staff WHERE ID = r.userID)) user_fullName,
                          (SELECT title FROM  library_2nd_edition.Documents WHERE ID = r.documentID) document_title
                     FROM library_2nd_edition.request r;""";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        LinkedList<Request> list = new LinkedList<>();
        while(rs.next()) {
            
            Request nextRequest = new Request(rs.getString(1),
                                                rs.getString(2),
                                           rs.getString(7),
                                             rs.getString(3),
                                          rs.getString(8),
                                          rs.getInt(4),
                                             rs.getString(5),
                                             rs.getString(6));
            
            list.add(nextRequest);
        }
        
        ps.close();
        rs.close();
        return list;
    }
    
     /**
     * This user make an act of borrow document. 
     * The quantity borrow is just only 1 each time.
     * 
     * @param documentID = borrowed document ID
     * 
     * @return false only if the quantity is not enough. In this case, false if no available copy
     * 
     * @throws SQLException 
     */
    public static boolean borrowDocument(String userID, String documentID) throws SQLException {
        int borrowQuantity = 1;
        
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call borrowDocument(?, ?, ?) }");
        
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
    
    public static boolean updateDateRequest(Request alterRequest) throws SQLException {
        String sql = """
                     UPDATE library_2nd_edition.request
                     SET borrowDate = ?,
                         returnDate = ?
                     WHERE requestID = ?""";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ps.setString(1, alterRequest.getBorrowDate());
        ps.setString(2, alterRequest.getReturnDate());
        ps.setString(3, alterRequest.getRequestID());
        
        return ps.executeUpdate() > 0;
    }
    
    /**
     * This user make an act of return their request.
     * 
     * @param requestID = 
     * 
     * @return false in what case???
     * 
     * @throws SQLException 
     */
    public static boolean returnDocument(String userID, String requestID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call returnDocument(?, ?) }");
        
        finder.setString(1, requestID);
        finder.setString(2, userID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            return rs.getBoolean("Result");
        }
        return false;
    }
    
    /**
     * Get a List<Request> being made by this user which not yet returned.
     * 
     * @return list of request found.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Request> getUnreturnDocument(String userID) throws SQLException {
        
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call checkForUnreturn_BookList(?) }");
        
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
    
    /**
     * Get all borrow history of this user. 
     * 
     * @return list of request found.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Request> getBorrowHistory(String userID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call checkForHistory_DocumentList(?) }");
        
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
    
    /**
     * Get List<Request> of all who borrow a Document
     * 
     * @param documentID = the document being borrowed ID.
     * 
     * @return list of request borrow that document.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Request> getUserBorrowThisDocument(String documentID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call checkForUnreturn_StudentList(?) }");
        
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
}
