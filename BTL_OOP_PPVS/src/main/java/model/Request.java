package Model;

import Controller.DBBook;
import java.sql.SQLException;


public class Request {
    private String requestID;
    private String userID;
    private Book book;
    private int quantityBorrow;
    private String borrowDate;
    private String returnDate;
    
    public Request() {}

    public Request(String requestID, String userID, String bookID, int quantityBorrow, String borrowDate, String returnDate) throws SQLException {
        this.requestID = requestID;
        this.userID = userID;
        this.book = DBBook.findBook(bookID);
        this.quantityBorrow = quantityBorrow;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    
    public Request(String requestID, String userID, Book book, int quantityBorrow, String borrowDate, String returnDate) { 
        this.requestID = requestID;
        this.userID = userID;
        this.book = book;
        this.quantityBorrow = quantityBorrow;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    
    

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    public void setBook(String bookID) throws SQLException {
        this.book = DBBook.findBook(bookID);
    }

    public int getQuantityBorrow() {
        return quantityBorrow;
    }

    public void setQuantityBorrow(int quantityBorrow) {
        this.quantityBorrow = quantityBorrow;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        String res = "Request " + requestID + "\nBook " + book + "quantityBorrow = " + quantityBorrow + "\nborrowDate = " + borrowDate;
        if (returnDate == null) {
            res = res + "\nNot Return yet";
        } else {
            res = res + "\nreturnDate = " + returnDate;
        }
        
        return res;
    }
    
    
}
