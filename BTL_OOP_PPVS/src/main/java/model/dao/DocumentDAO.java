/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.*;
import java.util.ArrayList;
import model.DatabaseConnector;
import model.entity.Document;

public class DocumentDAO {
    
    // 1. Lấy tất cả các tài liệu
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

    // 2. Lấy thông tin tài liệu chi tiết dựa trên ID
    public static Document getDocumentInfo(String ID) throws SQLException {
        String sql = "SELECT * FROM library_2nd_edition.documents WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ps.setString(1, ID);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
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
            ps.close();
            rs.close();
            return document;
        }
        
        ps.close();
        rs.close();
        return null;
    }

    // 3. Thêm mới tài liệu
    public static boolean addNewDocument(Document document) throws SQLException {
        String sql = "INSERT INTO library_2nd_edition.documents (ID, Title, Author, Publisher, Publication_year, Category, Language, Summary, File_image) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        
        ps.setString(1, document.getID());
        ps.setString(2, document.getTitle());
        ps.setString(3, document.getAuthor());
        ps.setString(4, document.getPublisher());
        ps.setString(5, document.getPublicationYear());
        ps.setString(6, document.getCategory());
        ps.setString(7, document.getLanguage());
        ps.setString(8, document.getSummary());
        ps.setString(9, document.getFileImage());
        
        int rowsInserted = ps.executeUpdate();
        ps.close();
        
        return rowsInserted > 0;
    }

    // 4. Cập nhật thông tin tài liệu
    public static boolean updateDocument(Document document) throws SQLException {
        String sql = "UPDATE library_2nd_edition.documents "
                   + "SET Title = ?, Author = ?, Publisher = ?, Publication_year = ?, Category = ?, Language = ?, Summary = ?, File_image = ? "
                   + "WHERE ID = ?";
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

    // 5. Xóa tài liệu
    public static boolean deleteDocument(Document document) throws SQLException {
        String sql = "DELETE FROM library_2nd_edition.documents WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        ps.setString(1, document.getID());
        
        int rowsDeleted = ps.executeUpdate();
        ps.close();
        
        return rowsDeleted > 0;
    }
}

