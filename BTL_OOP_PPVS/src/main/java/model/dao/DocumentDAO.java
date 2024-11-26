package model.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import model.DatabaseConnector;
import model.entity.Document;

public class DocumentDAO {
    
    // Lấy danh sách tất cả tài liệu
    public static ArrayList<Document> getAllDocuments() throws SQLException {
        String sql = "SELECT * FROM library_2nd_edition.documents";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Document> list = new ArrayList<>();
        while (rs.next()) {
            Document document = new Document(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9)
            );
            list.add(document);
        }

        ps.close();
        rs.close();
        return list;
    }
    
    // Lấy thông tin tài liệu theo ID
    public static Document getDocumentInfo(String documentID) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call getDocumentInfo(?) }");
        finder.setString(1, documentID);

        ResultSet rs = finder.executeQuery();
        if (rs.next()) {
            return new Document(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9)
            );
        }

        finder.close();
        rs.close();
        return null;
    }
    
    // Thêm một tài liệu mới
    public static boolean addNewDocument(Document document) throws SQLException {
        CallableStatement finder = DatabaseConnector.getConnection().prepareCall("{ call addDocument(?, ?, ?, ?, ?, ?, ?, ?, ?) }");

        finder.setString(1, document.getID());
        finder.setString(2, document.getTitle());
        finder.setString(3, document.getAuthor());
        finder.setString(4, document.getPublisher());
        finder.setString(5, document.getPublicationYear());
        finder.setString(6, document.getCategory());
        finder.setString(7, document.getLanguage());
        finder.setString(8, document.getSummary());
        finder.setString(9, document.getFileImage());

        // Thực thi thủ tục (không trả về ResultSet, chỉ thực thi và trả về số hàng bị ảnh hưởng)
        int rowsAffected = finder.executeUpdate();
        finder.close();

        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được thêm thành công.
    }
    
    // Cập nhật thông tin tài liệu
    public static boolean updateDocument(Document document) throws SQLException {
        String sql = "UPDATE library_2nd_edition.documents SET Title = ?, Author = ?, Publisher = ?, Publication_year = ?, Category = ?, Language = ?, Summary = ?, File_image = ? WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);


        ps.setString(1, document.getTitle());
        ps.setString(2, document.getAuthor());
        ps.setString(3, document.getPublisher());
        ps.setString(4, document.getPublicationYear());
        ps.setString(5, document.getCategory());
        ps.setString(6, document.getLanguage());
        ps.setString(7, document.getSummary());
        ps.setString(8, document.getFileImage());
        ps.setString(9, document.getID());

        int rowsUpdated = ps.executeUpdate();
        ps.close();

        return rowsUpdated > 0;
    }
    
    // Xóa tài liệu
    public static boolean deleteDocument(Document document) {
        String sql = "DELETE FROM library_2nd_edition.documents WHERE ID = ?";
        try (PreparedStatement stmt = DatabaseConnector.getConnection().prepareStatement(sql)) {
            stmt.setString(1, document.getID());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa tài liệu thành công! ID: " + document.getID());
                return true;
            } else {
                System.out.println("Không tìm thấy tài liệu cần xóa! ID: " + document.getID());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi xảy ra khi xóa tài liệu: " + e.getMessage());
            e.printStackTrace(); // Ghi log chi tiết lỗi
            return false;
        }
    }


}
