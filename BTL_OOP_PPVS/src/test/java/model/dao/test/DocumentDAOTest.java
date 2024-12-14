package model.dao.test;

import model.dao.DocumentDAO;
import model.entity.Book;
import model.entity.Document;
import model.dao.FileFormatException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DocumentDAOTest {

    @BeforeAll
    static void setup() {
        // Code để khởi tạo dữ liệu giả lập hoặc thiết lập trước khi chạy test
    }

    @AfterAll
    static void cleanup() {
        // Code để dọn dẹp dữ liệu sau khi chạy test
    }

    @Test 
    @Order(1)
    void testGetDocumentInfoNotFound() throws SQLException, IOException {
        String nonExistingDocumentId = "999999"; // Một ID không tồn tại
        Document document = DocumentDAO.getDocumentInfo(nonExistingDocumentId);
        assertNull(document, "Document should be null for non-existing ID");
    }

    @Test 
    @Order(2)
    void testAddBook() throws SQLException, IOException, FileFormatException, URISyntaxException {
        Book newBook = new Book(
            "987654321",
            "Test Book Title",
            10,
            "Test Author",
            "Test Publisher",
            2024,
            "Test Description",
            "Historical",
            "Test Language"
        );

        boolean result = DocumentDAO.addBook(newBook);
        if (result) {
            System.out.println("Thêm mẫu test thành công");
        }
        assertTrue(result, "Adding a book should return true");
    }

    @Test 
    @Order(3)
    void testAddDuplicateBook() throws SQLException, IOException, FileFormatException, URISyntaxException {
        Book duplicateBook = new Book(
            "987654321", // ID trùng với sách đã thêm
            "Duplicate Book Title",
            5,
            "Test Author",
            "Test Publisher",
            2024,
            "Test Description",
            1,
            "Test Language"
        );

        boolean result = DocumentDAO.addBook(duplicateBook);
        assertFalse(result, "Adding a duplicate book should return false");
    }
    
    @Test 
    @Order(4)
    void testGetDocumentInfo() throws SQLException, IOException {
        String existingDocumentId = "987654321"; // Thay thế bằng một ID thực tế có trong database
        Book document = (Book) DocumentDAO.getDocumentInfo(existingDocumentId);
        assertNotNull(document, "Document should not be null");
        
        System.out.println("Tìm được thông tin sách sau:");
        System.out.println(document.getID());
        System.out.println(document.getTitle());
        System.out.println(document.getAuthor());
        System.out.println(document.getPublisher());
        System.out.println(document.getCategory());
        assertEquals(existingDocumentId, document.getID(), "Document ID should match");
        
        // Thế dima ko ktra xem các thông tin kahcs nó lấy về có sai ko à
    }

    /* Bỏ test, vì hàm không dùng tới, out-of-date, chưa được cập nhật lại.
    @Test
    void testSearchBook() throws SQLException, IOException {
        String keyword = "Test"; // Từ khóa tìm kiếm sách
        ArrayList<Book> books = DocumentDAO.searchBook(keyword, keyword, 0, 0, "");
        assertNotNull(books, "Search result should not be null");
        assertFalse(books.isEmpty(), "Search result should not be empty");
    }
    */

    @Test   
    @Order(5)
    void testGetAllBook() throws SQLException, IOException {
        ArrayList<Book> books = DocumentDAO.getAllBook();
        assertNotNull(books, "Book list should not be null");
        assertFalse(books.isEmpty(), "Book list should not be empty");
    }

    @Test   
    @Order(6)
    void testDeleteDocument() throws SQLException, IOException {
        String documentId = "987654321"; // ID của sách vừa thêm
        DocumentDAO.deleteDocument(documentId);
        Document document = DocumentDAO.getDocumentInfo(documentId);
        
        assertNull(document, "Document should be null after deletion");
    }
}