package model.entity;

import java.sql.SQLException;

import java.util.ArrayList;

import model.dao.UserDAO;
import model.dao.RequestDAO;

/**
 *
 * @author Littl
 */
public abstract class User {
    public enum LoginAlert {
        FIRST_TIMES_LOGIN,
        CORRECT_PASSWORD_AS_STAFF,
        CORRECT_PASSWORD_AS_MEMBER,
        WRONG_PASSWORD
    }
    
    protected String ID;
    protected String firstName;
    protected String lastName;
    
    /**
     *<p>
     * Thực hiện login vào tài khoản thông qua ID và mật khẩu.
     * Kết quả trả về là User.loginAlert.
     *</p>
     *<p>
     * Hàm chỉ trả về kết quả thành công hay không.
     * Việc getInfo cho currentUser do view thực hiện lại.
     * </p>
     * 
     * @param userID = ID người dùng.
     * @param password = mật khẩu xác nhận.
     * 
     * @return  FIRST_TIMES_LOGIN, WRONG_PASSWORD, CORRECT_PASSWORD
     * 
     * @throws SQLException 
     */
    public static LoginAlert login(String userID, String password) throws SQLException {
        return UserDAO.login(userID, password);
    }
    
    /**
     * Cho phép tự cài lại mật khẩu của chính User này.
     * 
     * @param oldPassword = mật khẩu cũ
     * @param newPassword = mật khẩu mới
     * 
     * @return true/false
     */
    public boolean changeOwnPassword(String oldPassword, String newPassword) throws SQLException {
        return UserDAO.changePassword(this.ID, oldPassword, newPassword);
    }
    
    /**
     * Cài lại mật khẩu trong lần đầu đăng nhập.
     * 
     * Mật khẩu gốc mặc định là UserID
     * 
     * @param newPassword = mật khẩu mới
     * 
     * @return true/false
     */
    public boolean firstLogInChangePassword(String newPassword) throws SQLException {
        return UserDAO.changePassword(this.ID, this.ID, newPassword);
    }
    
    /**
     * Người dùng này thực hiện việc mượn tài liệu.
     * 
     * @param documentID = borrowed document ID
     * 
     * @return false only if the quantity is not enough. In this case, false if no available copy
     * 
     * @throws SQLException 
     */
    public boolean borrowDocument(String documentID) throws SQLException {
        return RequestDAO.borrowDocument(ID, documentID);
    }
    
    public boolean readDocument(String documentID) {
        return false;
    }
    
    /**
     * Thực hiên trả tài liệu
     * 
     * @param documentID = ID sách được thực hiện trả
     * 
     * @return false nếu không tìm được mã phiếu, do người dùng này không mượn quyển sách đó mà chưa trả.
     * 
     * @throws SQLException 
     */
    public boolean returnDocument(String documentID) throws SQLException {
        return RequestDAO.returnDocument(ID, documentID);
    }
    
    /**
     * Lấy thông tin phiếu yêu cầu chưa được trả của người dùng này.
     * 
     * @return list of request found.
     * 
     * @throws SQLException 
     */
    public ArrayList<Request> getMyUnreturnDocument() throws SQLException {
        return RequestDAO.getUnreturnDocument(this.ID);
    }
    
    /**
     * Lấy thông tin phiếu yêu cầu được thực hiện bởi người dùng này từ trước tới nay.
     * 
     * @return list of request found.
     * 
     * @throws SQLException 
     */
    public ArrayList<Request> getMyBorrowHistory() throws SQLException {
        return RequestDAO.getBorrowHistory(ID);
    }
    

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public abstract String getContact();
    
    public abstract void setContact(String contact);
}
