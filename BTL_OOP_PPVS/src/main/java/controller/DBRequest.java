package controller;


import java.sql.ResultSet;
import java.sql.CallableStatement;


import model.Request;
import java.io.File;

import java.util.ArrayList;

import java.sql.SQLException;
import java.io.IOException;

public class DBRequest extends DatabaseConnector {
    
    
    /**
     * Execute borrow Book.
     * 
     * @param userID = user who borrow
     * @param bookID = the book want to borrow
     * @param quantity = quantityBorrow
     * 
     * @return true if the work done.
     * 
     * @throws SQLException 
     */
    public static boolean borrowBook(String userID, String bookID, int quantity) throws SQLException, IOException {
        ResultSet rs;
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call borrowBook(?, ?, ?) }");
        
        finder.setString(1, bookID);
        finder.setString(2, userID);
        finder.setString(3, String.valueOf(quantity));
        
        rs = finder.executeQuery();
        
        while (rs.next()) {
            if (rs.getBoolean("Result")) {
                
                UpdateFile.saveBookFile(bookID);
                
                return true;
            }
            return false;
        }
        return false;
    } 
    
    
    /**
     * Execute Return Book request. 
     * 
     * @param requestID = request when you borrow.
     * 
     * @return true if right user return the Book (like admin or user who borrow)
     * 
     * @throws SQLException 
     */
    public static boolean returnBook (Request req) throws SQLException {
        ResultSet rs;
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call returnBook(?, ?) }");
        
        finder.setString(1, req.getRequestID());
        finder.setString(2, req.getUserID());
        
        rs = finder.executeQuery();
        
        while(rs.next()) {
            if (rs.getBoolean("Result") == true) {
                File thisBook = new File("src/OuterData/" + req.getBook().getId() + ".pdf");
                
                thisBook.delete();
                return true;
            }
            return false;
        }
        return false;
    }
    
    
    /**
     * Execute find every un-return request user have.
     * 
     * @param userID = ID of the user want to find.
     * 
     * @return An ArrayList of these request.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Request> UnreturnBookList(String userID) throws SQLException {
        ResultSet rs;
        ArrayList<Request> ret = new ArrayList<>();
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call checkForUnreturn_BookList(?) }");
        
        finder.setString(1, userID);
        
        rs = finder.executeQuery();
        
        while(rs.next()) {
            ret.add(new Request(rs.getString("requestID"), userID, rs.getString("bookID"), rs.getInt("quantityBorrow"), rs.getString("borrowDate"), null));
        }
        
        return ret;
    }
    
    
    /**
     * Execute find every un-return request user have.
     * 
     * @param userID = ID of the user want to find.
     * 
     * @return An ArrayList of these request.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Request> everBorrowBookList(String userID) throws SQLException {
        ResultSet rs;
        ArrayList<Request> ret = new ArrayList<>();
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call checkForHistory_BookList(?) }");
        
        finder.setString(1, userID);
        
        rs = finder.executeQuery();
        
        while(rs.next()) {
            ret.add(new Request(rs.getString("requestID"), userID, rs.getString("bookID"), rs.getInt("quantityBorrow"), rs.getString("borrowDate"), rs.getString("returnDate")));
        }
        
        return ret;
    }
}
