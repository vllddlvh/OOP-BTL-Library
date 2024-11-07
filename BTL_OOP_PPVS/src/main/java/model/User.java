package Model;


import java.sql.SQLException;
import java.io.IOException;

import java.util.ArrayList;

import Controller.DBLogin;
import Controller.DBUser;
import Controller.DBRequest;

public class User {
    public final static int SET_STUDENT = 0;
    public final static int SET_ADMIN = 1;
    public final static int SET_AUTHOR = 2;

    public static enum logIn {
        Correct,
        Incorrect,
        First_Login
    }
    
    protected String ID;
    protected int right = SET_STUDENT;
    
    
    
    public User(){}

    public User(String userID) {
        this.ID = userID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String userID) {
        this.ID = userID;
    }

    public int getRight() {
        return right;
    }

    private void setRight(int right) {
        this.right = right;
    }
    
    @Override
    public String toString() {
        return "User " + ID;
    }
    
    /**
     * Log in into the account you put.
     * <h2>If first LogIn situation, you must repair this method.<h2>
     * 
     * @param ID = userID to log in (mostly studentID).
     * @param password = password.
     * 
     * @return true if correct.
     */
    public logIn logIn(String ID, String password) {
        try {
            switch (DBLogin.logIn(ID, password)) {
                case DBLogin.TRUE_LOG_IN:
                    return logIn.Correct;
                case DBLogin.FAIL_LOG_IN:
                    return logIn.Incorrect;
                case DBLogin.FIRST_LOG_IN:
                    return logIn.First_Login;
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return logIn.Incorrect;
    }
    
    
    /**
     * Get full detail information of this user. Whatever the right
     * With toString been Override too.
     * 
     * @return another var contain full information for this.
     */
    public User getInfo() {
        try {
            return DBUser.getUserInfo(this);
            
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return null;
    }
    
    public boolean changeUserName(String newUserName) {
        try {
            DBUser.changeUserName(this.ID, newUserName);
            return true;
            
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return false;
    }
    
    public boolean changeContact(String newContact) {
        try {
            DBUser.changeUserName(this.ID, newContact);
            return true;
            
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return false;
    }
    
    public boolean changePassword(String oldPassword, String newPassword) {
        try {
            return DBLogin.changePassword(this.ID, oldPassword, newPassword) == DBLogin.TRUE_LOG_IN;
            
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return false;
    }
    
    /**
     * Borrow the book determine by bookID.
     * 
     * @param bookID = ID of the book
     * @param quantity = quantity you want to borrow.
     * 
     * @return if the book not Out of Stock, then true.
     * 
     * @throws SQLException 
     */
    public boolean borrowBook(String bookID, int quantity) throws SQLException, IOException, Exception {
        return DBRequest.borrowBook(this.ID, bookID, quantity);
    }
    
    
    /**
     * Apply a return quest determine by requestID receive when you borrow
     * 
     * @param thisRequest = This request.
     * 
     * @return true if the return done.
     * 
     * @throws SQLException 
     */
    public boolean returnBook (Request thisRequest) throws SQLException {
        return DBRequest.returnBook(thisRequest);
    }
    
    
    /**
     * Get your Un-returned Books List.
     * 
     * @return An ArrayList of request.
     * 
     * @throws SQLException 
     */
    public ArrayList<Request> getMyUnreturnBook() throws SQLException {
        return DBRequest.UnreturnBookList(this.ID);
    }
    
    
    /**
     * Get your full history of ever borrowed Books List.
     * 
     * @return An ArrayList of request.
     * 
     * @throws SQLException 
     */
    public ArrayList<Request> getMyHistory() throws SQLException {
        return DBRequest.everBorrowBookList(this.ID);
    }
}
