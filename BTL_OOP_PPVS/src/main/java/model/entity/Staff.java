package model.entity;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import java.util.ArrayList;

import model.dao.StaffDAO;
import model.dao.UserDAO;
import model.dao.DocumentDAO;
import model.dao.FileFormatException;
import model.dao.RequestDAO;


/**
 *
 * @author Littl
 */
public class Staff extends User {
    protected String contact = "example@staff.ppvs";
    protected String jobTitle = "Librarian";
    protected String reportTo = "U001";

    /*
    Contructor and get Own Information.
    */
    
    /**
     * Contructor rút gọn. Để test.
     * 
     * @param ID = ID quản lý mới.
     * @param firstName = tên riêng.
     * @param lastName = họ, tên đệm.
     */
    public Staff(String ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Staff(String ID, String firstName, String lastName, String contact, String jobTitle, String reportTo) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.jobTitle = jobTitle;
        this.reportTo = reportTo;
    }
    
    /**
     * Lấy thông tin nhân viên quản lý (Staff) từ csdl thông qua UserID.
     * 
     * @param ID = ID người dùng
     * 
     * @throws SQLException 
     */
    public Staff(String ID) throws SQLException {
        Staff getter = StaffDAO.getStaffInfo(ID);
        
        if (getter != null) {
            this.ID = getter.ID;
            firstName = getter.firstName;
            lastName = getter.lastName;
            contact = getter.contact;
            jobTitle = getter.jobTitle;
            this.reportTo = getter.reportTo;
        }
    }
    
    
    /*
    Function
    */
    
    
    /**
     * Lấy thông tin của Member khác thông qua ID của họ.
     * 
     * @param ID = that member ID.
     * 
     * @return an model.Member object contain information.
     * return null if there's no such userID
     * 
     * @throws SQLException 
     */
    public Member getMemberInfo(String ID) throws SQLException {
        return UserDAO.getMemberInfo(ID);
    }
    
    /**
     * Lấy thông tin của một nhân viên quản lý khác bất kỳ dựa theo ID.
     * 
     * @param ID = that staff ID.
     * 
     * @return an model.Staff object contain information.
     * return null if there's no such userID
     * 
     * @throws SQLException 
     */
    public Staff getStaffInfo(String ID)throws SQLException {
        return StaffDAO.getStaffInfo(ID);
    }
    
    
    /**
     * Lấy thông tin của một Staff/Member nói chung.
     * Thực hiện xác định theo UserID.
     * 
     * @param ID = that staff ID.
     * 
     * @return an model.Staff object contain information.
     * return null if there's no such userID
     * 
     * @throws SQLException 
     */
    public User getUserInfo(String ID) throws SQLException {
        User target = getMemberInfo(ID);
        if (target == null) {
            target = getStaffInfo(ID);
        }
        return target;
    }
    
    /**
     * Thêm thành viên mới vào csdl
     * 
     * @param newMember = that new Member.
     * 
     * @return false if duplicate userID.
     * 
     * @throws SQLException 
     */
    public boolean addNewMember (Member newMember) throws SQLException {
        return UserDAO.addNewMember(newMember);
    }
    
    /**
     * Thêm nhân viên quản lý mới. 
     * Người này sẽ mặc định reportTo (được quản lý bởi) nhân viên đã thêm họ vào.
     * 
     * @param newStaff = that new comer.
     * 
     * @return false if duplicate userID.
     * 
     * @throws SQLException 
     */
    public boolean addNewStaff (Staff newStaff) throws SQLException {
        return StaffDAO.addNewStaff(newStaff, this.ID);
    }
    
    /**
     * Xóa người dùng, bất kể Staff hay Member.
     * Vì chưa có chức năng quản lý giữa các Staff, nên tạm thời bỏ qua ID người chịu trách nhiệm.
     * 
     * @param ID = ID người bị xóa.
     * 
     * @throws SQLException 
     */
    public void deleteUser(String ID) throws SQLException {
        UserDAO.deleteUser(ID);
    }
    
    /**
     * Thêm sách mới vào database.
     * 
     * @param newBook = only Book object have been full construct accept.
     * 
     * @return false if duplicate documentID.
     * 
     * @throws SQLException 
     * @throws IOException
     * @throws model.dao.FileFormatException
     * @throws java.net.URISyntaxException
     */
    public boolean addBook(Book newBook) 
            throws SQLException, IOException, FileFormatException, URISyntaxException  {
        return DocumentDAO.addBook(newBook);
    }
    
    /**
     * Thêm tài liệu nói chung vào database (Cụ thể là Book hoặc Thesis)
     * 
     * @param newDocument = only Book or Thesis object have been full construct accept.
     * 
     * @return false if duplicate documentID.
     * 
     * @throws SQLException 
     * @throws IOException
     * @throws model.dao.FileFormatException
     * @throws java.net.URISyntaxException
     */
    public boolean addDocument(Document newDocument) 
            throws SQLException, IOException, FileFormatException, URISyntaxException {
        if (newDocument instanceof Book) {
           return addBook((Book) newDocument);
        } 
        return false;
    }
    
    /**
     * Chỉnh sửa lượng sách hiện có của thư viện.
     * 
     * @param documentID = ID tài liệu cần chỉnh sửa.
     * @param quantityChange = Lượng tài liệu được thêm vào (bớt đi thì nhập số âm).
     * 
     * @throws SQLException 
     */
    public void loadMoreCopies(String documentID, int quantityChange) throws SQLException {
        DocumentDAO.loadMoreCopies(documentID, quantityChange);
    }
    
    /**
     * Delete a document determine by its ID.
     * 
     * @param documentID = ID of the document want to be deleted.
     * 
     * @throws SQLException 
     */
    public void deleteDocument(String documentID) throws SQLException  {
        DocumentDAO.deleteDocument(documentID);
    }
    
    /**
     * Get a List<Request> being made by an user which not yet returned.
     * 
     * @param userID = ID of the user who borrow.
     * 
     * @return list of request found.
     * 
     * @throws SQLException 
     */
    public ArrayList<Request> getUnreturnDocument(String userID) throws SQLException {
        return RequestDAO.getUnreturnDocument(userID);
    }
    
    /**
     * Get all borrow history of an user. 
     * 
     * @param userID = that user ID.
     * 
     * @return list of request found.
     * 
     * @throws SQLException 
     */
    public ArrayList<Request> getBorrowHistory(String userID) throws SQLException {
        return RequestDAO.getBorrowHistory(userID);
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
    public ArrayList<Request> getUserBorrowThisDocument(String documentID) throws SQLException {
        return RequestDAO.getUserBorrowThisDocument(documentID);
    }
    
    
    /*
    Getter/Setter.
    */
    
    @Override
    public String getContact() {
        return contact;
    }

    @Override
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getReportTo() {
        return reportTo;
    }

    public void setReportTo(String reportTo) {
        this.reportTo = reportTo;
    }
    
    
}
