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
    
    /**
     * Clone thông tin của một phiếu yêu cầu (Request)
     * 
     * @param org = mẫu gốc.
     */
    public Request(Request org) {
        this.requestID = org.requestID;
        this.userID = org.userID;
        this.documentID = org.documentID;
        this.quantityBorrow = org.quantityBorrow;
        this.borrowDate = org.borrowDate;
        this.returnDate = org.returnDate;
        this.user_fullName = org.user_fullName;
        this.document_title = org.document_title;
    }
    
    /**
     * Contructor đầy đủ để khởi tạo một phiếu yêu cầu mới
     * 
     * @param requestID
     * @param userID
     * @param documentID
     * @param quantityBorrow
     * @param borrowDate
     * @param returnDate 
     */
    public Request(String requestID, String userID, String documentID, int quantityBorrow, String borrowDate, String returnDate) { 
        this.requestID = requestID;
        this.userID = userID;
        this.documentID = documentID;
        this.quantityBorrow = quantityBorrow;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    
    /**
     * Contructor bổ sung để lấp khoảng trống.
     * Bổ sung thêm user_fullName, document_title.
     * Về cơ bản thì 2 thông tin thêm không phải lúc nào cũng cần thiết.
     * 
     * Thông tin ngày mượn/trả sẽ dược database tự set là ngày hiện tại nếu không có chỉnh sửa thêm.
     * 
     * @param requestID = mã phiếu yêu cầu.
     * @param userID = ID người thực hiện (mượn/trả sách).
     * @param user_fullName = tên dầy đủ của người mượn.
     * @param documentID = ID sách được mượn.
     * @param document_title = tiêu đề sách.
     * @param quantityBorrow = số lượng mượn, mặc định cứ để là 1 nha.
     * @param borrowDate = ngày mượn.
     * @param returnDate = ngày trả
     */
    public Request(String requestID, String userID, String user_fullName, 
                                     String documentID, String document_title, 
                                     int quantityBorrow, String borrowDate, String returnDate) { 
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
