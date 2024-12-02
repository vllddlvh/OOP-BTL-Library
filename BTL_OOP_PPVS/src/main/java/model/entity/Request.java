package model.entity;



public class Request {
    private String requestID;
    private String userID;
    private String documentID;
    private int quantityBorrow;
    private String borrowDate;
    private String returnDate;
    
    private String user_fullName = null;
    private String document_title = null;
    
    public Request(String requestID, String userID, String documentID, int quantityBorrow, String borrowDate, String returnDate) { 
        this.requestID = requestID;
        this.userID = userID;
        this.documentID = documentID;
        this.quantityBorrow = quantityBorrow;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    
    public Request(String requestID, String userID, String user_fullName, String documentID, String document_title, int quantityBorrow, String borrowDate, String returnDate) { 
        this.requestID = requestID;
        this.userID = userID;
        this.documentID = documentID;
        this.quantityBorrow = quantityBorrow;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.user_fullName = user_fullName;
        this.document_title = document_title;
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

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String bookID) {
        this.documentID = bookID;
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

    public String getUser_fullName() {
        return user_fullName;
    }

    public String getDocument_title() {
        return document_title;
    }
    
    @Override
    public String toString() {
        String res = "Request " + requestID + "\nBook " + documentID + "quantityBorrow = " + quantityBorrow + "\nborrowDate = " + borrowDate;
        if (returnDate == null) {
            res = res + "\nNot Return yet";
        } else {
            res = res + "\nreturnDate = " + returnDate;
        }
        
        return res;
    }
}
