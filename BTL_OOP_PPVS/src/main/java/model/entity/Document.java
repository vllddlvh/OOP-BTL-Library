/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DatabaseConnector;

/**
 *
 * @author HP
 */
public class Document {
    private String ID;
    private String title;
    private String author;
    private String publisher;
    private String publicationYear;
    private String category;
    private String language;
    private String summary;
    private String fileImage;

    // Constructor
    public Document(String ID, String title, String author, String publisher, String publicationYear, String category, String language, String summary, String fileImage) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.language = language;
        this.summary = summary;
        this.fileImage = fileImage;
    }

    public Document(String documentID) throws SQLException {
    // Chuẩn bị câu lệnh CallableStatement để gọi stored procedure
    CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call getDocumentInfo(?) }");

    // Gán giá trị tham số ID vào stored procedure
    finder.setString(1, documentID);

    ResultSet rs = finder.executeQuery();

    // Lấy dữ liệu từ ResultSet và gán vào các thuộc tính của Document
    while (rs.next()) {
        this.ID = rs.getString(1);             // Gán ID của Document
        this.title = rs.getString(2);         // Gán tiêu đề
        this.author = rs.getString(3);        // Gán tác giả
        this.publisher = rs.getString(4);     // Gán nhà xuất bản
        this.publicationYear = rs.getString(5); // Gán năm xuất bản
        this.category = rs.getString(6);      // Gán danh mục
        this.language = rs.getString(7);      // Gán ngôn ngữ
        this.summary = rs.getString(8);
        this.fileImage = rs.getString(9);
    }
}

    // Getters and Setters
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getPublicationYear() { return publicationYear; }
    public void setPublicationYear(String publicationYear) { this.publicationYear = publicationYear; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getFileImage() { return fileImage; }
    public void setFileImage(String fileImage) { this.fileImage = fileImage; }
}
