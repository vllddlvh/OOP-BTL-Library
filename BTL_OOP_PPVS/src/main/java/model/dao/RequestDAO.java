package model.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DatabaseConnector;
import model.entity.Request;


public class RequestDAO {
    
    /**
     * Lấy toàn bộ lưu trữ phiếu yêu cầu trong CSDL.
     * Bao gồm thông tin của phiếu đó, và tên sách/ tên người mượn.
     * Mấy cái tên để thừa thôi, nhưng nhìn toàn số không thôi thì cũng khó.
     * 
     * @return danh sách các phiếu yêu cầu này.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Request> getAllRequest() throws SQLException {
        String sql = """
                     SELECT r.*,
                          coalesce((SELECT CONCAT_WS(" ", lastName, firstName) FROM  library_2nd_edition.Member WHERE ID = r.userID),
                          (SELECT CONCAT_WS(" ", lastName, firstName) FROM  library_2nd_edition.Staff WHERE ID = r.userID)) user_fullName,
                          (SELECT title FROM  library_2nd_edition.Documents WHERE ID = r.documentID) document_title
                     FROM library_2nd_edition.request r;""";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Request> list = new ArrayList<>();
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
     * Tiến hành mượn sách cho người dùng này. Số lượng mượn mặc định là 1.
     * 
     * @param userID = ID người dùng mượn sách.
     * @param documentID = ID sách cần mượn.
     * 
     * @return false nếu không còn đủ số lượng sách để cho mượn.
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
    
    /**
     * Chỉnh sửa thông tin phiếu yêu cầu.
     * Chính xác thì chỉ chỉnh được ngày mượn/trả thôi.
     * Sửa thông tin khác sợ bị xung đột.
     * 
     * @param alterRequest
     * @return
     * @throws SQLException 
     */
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
     * Thực hiện trả sách. Thực chất là điền vào vị trí returnDate hiện đang là null.
     * 
     * @param userID = mã người dùng đã mượn sách.
     * @param documentID = mã sách được mượn
     * 
     * @return false nếu không tìm được mã phiếu, do người dùng đó không mượn quyển sách đó mà chưa trả.
     * 
     * @throws SQLException 
     */
    public static boolean returnDocument(String userID, String documentID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call returnDocument(?, ?) }");
        
        finder.setString(1, documentID);
        finder.setString(2, userID);
        
        if (finder.executeUpdate() > 0) {
            File pdf = new File("src/OuterData/" + documentID + ".pdf");
            if (pdf.exists()) {
                pdf.delete();
            }
            return true;
        }
        return false;
    }
    
    /**
     * Toàn bộ danh sách các phiếu yêu cầu chưa trả của người dùng này.
     * 
     * @param userID = ID người dùng cần check.
     * 
     * @return danh sách các phiếu yêu cầu chưa trả của người này.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Request> getUnreturnDocument(String userID) throws SQLException {
        
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call checkForUnreturn_DocumentList(?) }");
        
        finder.setString(1, userID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        ArrayList<Request> req = new ArrayList<>();
        while(rs.next()) {
            req.add(new Request(rs.getString(1), 
                                        userID, 
                             rs.getString(2),
                               rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5), 
                               rs.getString(6), 
                               null));
        }
        
        return req;
    }
    
    /**
     * Lấy toàn bộ lích sử mượn sách của người này. Bao gồm cả phiếu đã trả và chưa trả.
     * 
     * @param userID = ID người dùng cần check.
     * 
     * @return danh sách phiếu yêu cầu trong toàn bộ lích sử mượn sách của người này.
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
                               rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5), 
                               rs.getString(6), 
                               rs.getString(7)));
        }
        
        return req;
    }
    
    /**
     * Lấy toàn bộ danh sách các phiếu yêu cầu mượn quyển sách này mà vẫn chưa trả.
     * 
     * @param documentID = ID sách cần check.
     * 
     * @return danh sách phiếu yêu cầu trong toàn bộ lích sử mượn sách của người này.
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
    
    /**
     * Lấy thông tin của chính xác một phiếu yêu cầu nhất định, dựa theo requestID.
     * Bao gồm thông tin chính như: mã phiếu, userID, documentID, borrowDate, returnDate.
     * Bổ sung thêm tên sách/ tên người mượn cho đỡ trống.
     * 
     * @param requestID = mã phiếu yêu cầu.
     * 
     * @return Request đó.
     * 
     * @throws SQLException 
     */
    public static Request getRequest(String requestID) throws SQLException {
        String sql = """
                     SELECT r.*,
                          coalesce((SELECT CONCAT_WS(" ", lastName, firstName) FROM  library_2nd_edition.Member WHERE ID = r.userID),
                            (SELECT CONCAT_WS(" ", lastName, firstName) FROM  library_2nd_edition.Staff WHERE ID = r.userID)) user_fullName,
                          (SELECT title FROM  library_2nd_edition.Documents WHERE ID = r.documentID) document_title
                     FROM library_2nd_edition.request r
                     WHERE r.requestID = ?""";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ps.setString(1, requestID);
        ResultSet rs = ps.executeQuery();
        
        Request result = null;
        while(rs.next()) {
            
            result = new Request(rs.getString(1),
                                   rs.getString(2),
                              rs.getString(7),
                                rs.getString(3),
                             rs.getString(8),
                             rs.getInt(4),
                                rs.getString(5),
                                rs.getString(6));
        }
        
        ps.close();
        rs.close();
        return result;
    }
}
