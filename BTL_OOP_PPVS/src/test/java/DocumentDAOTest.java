import model.dao.DocumentDAO;
import model.entity.Book;
import model.entity.Document;
import model.dao.FileFormatException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

class DocumentDAOTest {

    @BeforeAll
    static void setup() {
        // Code để khởi tạo dữ liệu giả lập hoặc thiết lập trước khi chạy test
    }

    @AfterAll
    static void cleanup() {
        // Code để dọn dẹp dữ liệu sau khi chạy test
        // Đàn đi làm cái này
    }

    @Test
    void testGetDocumentInfo() throws SQLException, IOException {
        String existingDocumentId = "006"; // Thay thế bằng một ID thực tế có trong database
        Book document = (Book) DocumentDAO.getDocumentInfo(existingDocumentId);
        System.out.println(document.getID());
        System.out.println(document.getTitle());
        System.out.println(document.getAuthor());
        System.out.println(document.getPublisher());
        System.out.println(document.getCategory());
        assertNotNull(document, "Document should not be null");
        assertEquals(existingDocumentId, document.getID(), "Document ID should match");
        
        // Thế dima ko ktra xem các thông tin kahcs nó lấy về có sai ko à
    }

    @Test
    void testGetDocumentInfoNotFound() throws SQLException, IOException {
        String nonExistingDocumentId = "999999"; // Một ID không tồn tại
        Document document = DocumentDAO.getDocumentInfo(nonExistingDocumentId);
        assertNull(document, "Document should be null for non-existing ID");
        
        // mẹ m. Commit về ko để ý tên có ghi là đổi db ko à. là 
    }

    @Test
    void testAddBook() throws SQLException, IOException, FileFormatException {
        Book newBook = new Book(
            "987654321",
            "Test Book Title",
            10,
            "Test Author",
            "Test Publisher",
            2024,
            "Test Description",
            1, // Test Category Encrypt // ai bào m nhập bằng enccrypt hả e =))sos
            "Test Language"
        );

        boolean result = DocumentDAO.addBook(newBook);
        assertTrue(result, "Adding a book should return true");
    }

    @Test
    void testAddDuplicateBook() throws SQLException, IOException, FileFormatException {
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
    void testSearchBook() throws SQLException, IOException {
        String keyword = "Test"; // Từ khóa tìm kiếm sách
        ArrayList<Book> books = DocumentDAO.searchBook(keyword, "", 0, 0, "");
        assertNotNull(books, "Search result should not be null");
        assertFalse(books.isEmpty(), "Search result should not be empty");
    }

    @Test
    void testGetAllBook() throws SQLException, IOException {
        ArrayList<Book> books = DocumentDAO.getAllBook();
        assertNotNull(books, "Book list should not be null");
        assertFalse(books.isEmpty(), "Book list should not be empty");
    }

    @Test
    void testDeleteDocument() throws SQLException, IOException {
        String documentId = "987654321"; // ID của sách vừa thêm
        DocumentDAO.deleteDocument(documentId);
        Document document = DocumentDAO.getDocumentInfo(documentId);
        
        assertNull(document, "Document should be null after deletion");
    }
}