package model.dao;


import java.awt.Image;
import java.io.IOException;
import java.net.URISyntaxException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseConnector;
import model.entity.Book;
import model.entity.Document;



/**
 *
 * @author Littl
 */
public abstract class DocumentDAO {
    
    /**
     * Lấy thông tin từ Database của Document (có thể là Book hoặc Thesis).
     * Thông tin được lấy dựa theo ID của tài liệu.
     * Nếu không có tài liệu với ID phù hợp, trả về null.
     * 
     * @param documentID = Thesis ID / Book ISBN.
     * 
     * @return Document object.
     * 
     * @throws SQLException 
     * @throws IOException
     */
    public static Document getDocumentInfo(String documentID) throws SQLException, IOException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call searchDocument(?) }");
        
        finder.setString(1, documentID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            switch(rs.getString("genre")) {
                case "Book" -> {
                    return new Book(rs.getString(1),
                                    rs.getString(2),
                            rs.getInt(3),
                                  rs.getString(4),
                                rs.getString(5),
                               rs.getInt(6),
                               rs.getString(7),
                                 rs.getInt(8),
                                 rs.getString(9));
                } 
                default -> {
                    return null;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Thay đổi số lượng lưu trữ của một loại tài liệu.
     * Tuy nhiên, hàm không check lại số lượng sách hiện có và số lượng sách này đã cho mượn.
     * Người dùng phải tự cẩn thận.
     * 
     * @param documentID
     * @param quantityChange
     * @throws SQLException 
     */
    public static void loadMoreCopies(String documentID, int quantityChange) throws SQLException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call deleteDocument(?) }");
        
        finder.setString(1, documentID);
        
        finder.executeQuery();
    }
    
    /**
     * Xóa một tài liệu khỏi database vĩnh viễn.
     * 
     * @param documentID = ID of the document want to be deleted.
     * 
     * @throws SQLException 
     */
    public static void deleteDocument(String documentID) throws SQLException  {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call deleteDocument(?, ?) }");
        
        finder.setString(1, documentID);
        finder.setString(2, "23021686");
        
        finder.executeQuery();
    }
    
    
    /**
     * Lấy toàn bộ Book (Sách) trên database.
     * Phía view sẽ filter và sort toàn bộ đống đó sau.
     * 
     * @return = một List chưa toàn bộ Book 
     * 
     * @throws SQLException
     * @throws IOException 
     */
    public static ArrayList<Book> getAllBook() throws SQLException, IOException {
        String sql = """
                        SELECT
                     \t\t   books.ISBN AS ISBN,
                     \t\t   documents.title AS title,
                     \t\t   documents.quantityLeft AS quantityAvailable,
                     \t\t   books.author AS author,
                     \t\t   books.publisher AS publisher,
                     \t\t   books.releaseYear AS releaseYear,
                     \t\t   documents.Description AS Description,
                     \t\t   documents.category AS category,
                     \t\t   documents.language AS language
                     \t  FROM books left join documents ON (books.isbn = documents.ID)""";
        ResultSet rs;
        ArrayList<Book> list;
        try (PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql)) {
            rs = ps.executeQuery();
            list = new ArrayList<>();
            while(rs.next()) {
                
                Book nextBook = new Book(rs.getString(1),
                                        rs.getString(2),
                                 rs.getInt(3),
                                       rs.getString(4),
                                     rs.getString(5),
                                    rs.getInt(6),
                                    rs.getString(7),
                                      rs.getInt(8),
                                      rs.getString(9));
                list.add(nextBook);
            }
        }
        rs.close();
        return list;
    }
    
    /**
     * Hàm tìm kiếm gốc trên database, nhưng hiện không sử dụng.
     * Chỉnh sửa hơi xa rồi, mình không còn lưu tài liệu sử dụng cái này như nào nữa
     * 
     * @param titleKeyword = searching by %title%.
     * @param authorKeyword = searching by %author%.
     * @param releaseYear = search by releaseYear. Sorry, not search on range of Years.
     * @param category = on format of model.entity.Document.BookCategory nested class.
     * @param language = book's language.
     * 
     * @return List of books found.
     * 
     * @throws SQLException 
     * @throws java.io.IOException 
     */
    public static ArrayList<Book> searchBook(String titleKeyword, 
                                            String authorKeyword, 
                                            int releaseYear, 
                                            int category, 
                                            String language) 
                                                            throws SQLException, IOException {
        CallableStatement finder = (CallableStatement) 
                DatabaseConnector.getConnection().prepareCall("{ call searchBook(?, ?, ?, ?, ?) }");
        
        finder.setString(1, titleKeyword);
        finder.setString(2, authorKeyword);
        finder.setInt(3, releaseYear);
        if (category != 0) {
            finder.setInt(4, category);
        } else {
            finder.setString(4, null);
        }
        finder.setString(5, language);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        ArrayList<Book> found = new ArrayList<>();
        while (rs.next()) {
            found.add(new Book(rs.getString(1),
                              rs.getString(2),
                       rs.getInt(3),
                             rs.getString(4),
                           rs.getString(5),
                          rs.getInt(6),
                          rs.getString(7),
                            rs.getInt(8),
                            rs.getString(9)));
        }
        
        return found;
    }
    
    /**
     * Thêm sách vào cơ sở dữ liệu. 
     * Sau khi thêm thông tin text, sẽ chuển qua upload cả file pdf và ảnh bìa, nếu có.
     * Tùy vào mức độ hoàn thiện thông tin của quyển sách, có thể ném ra một số lỗi.
     * Ví dụ: file pdf quá lớn trên 16MB, lỗi truy vấn (định dạng ngày tháng, giới hạn ký tự,...)
     * Hơi khó nhưng giới hạn ký tự vẫn xảy ra với title size > 100.
     * 
     * @param newBook = một Book với thông tin cơ bản được điền đầy đủ.
     * 
     * @return false khi và chỉ khi trùng lặp ID sách.
     * 
     * @throws SQLException 
     * @throws IOException
     * @throws model.dao.FileFormatException
     * @throws java.net.URISyntaxException
     */
    public static boolean addBook(Book newBook) 
            throws SQLException, IOException, FileFormatException, URISyntaxException  {
        
        CallableStatement finder = (CallableStatement) 
                DatabaseConnector.getConnection().prepareCall("{ call addBook(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
        
        finder.setString(1, newBook.getID());
        finder.setString(2, newBook.getTitle());
        finder.setString(3, newBook.getAuthor());
        finder.setInt(4, newBook.getAvailableCopies());
        finder.setInt(5, newBook.getCategoryEncrypt());
        finder.setString(6, newBook.getDescription());
        finder.setString(7, newBook.getPublisher());
        finder.setInt(8, newBook.getReleaseYear());
        finder.setString(9, newBook.getLanguage());
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while(rs.next()) {
            if (rs.getBoolean(1) == false) {
                return false;
                // nếu hàm trả về kết quả false, không thêm sách được.
            }
            
            if (newBook.getCover() != null) {
                FileHandle.uploadDocumentCover(newBook.getID(), newBook.getCover(), newBook.getCoverFormat());
            }
            if (newBook.getPDF() != null) {
                if (newBook.getCover() == null) {
                    Image tempCover = FileHandle.getFirstPage(FileHandle.convertURL_File(newBook.getPDF()));
                    newBook.setCover(tempCover, "png");
                }
                FileHandle.uploadDocumentPDF(newBook.getID(), newBook.getPDF(), newBook.getCover() == null);
            }
            return true;
        }
        
        return false;
    }
    
    /**
     * Chỉnh sửa thông tin cho một quyển sách. 
     * Tiến hành chỉnh sửa cả file pdf và ảnh bìa sau đó, nếu có bị thay đổi.
     * 
     * @param alter = mẫu kết quả cần đạt được sau chỉnh sửa.
     * 
     * @return true nếu sửa thành công.
     * 
     * @throws SQLException
     * @throws IOException 
     * @throws model.dao.FileFormatException 
     * @throws java.net.URISyntaxException 
     */
    public static boolean updateBook(Book alter) throws SQLException, IOException, FileFormatException, URISyntaxException {
        String sql1 = """
                     update documents
                     set title = ?, Description = ?, category = ?, language = ?
                     WHERE ID = ?;""";
                     
        String sql2 = """
                     update books
                     set author = ?, publisher = ?, releaseYear = ?
                     WHERE ISBN = ?;""";
                     
                     //update storedDocuements""";
        try (PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql1)) {
            ps.setString(1, alter.getTitle());
            ps.setString(2, alter.getDescription());
            ps.setInt(3, alter.getCategoryEncrypt());
            ps.setString(4, alter.getLanguage());
            ps.setString(5, alter.getID());
            ps.executeUpdate();
            ps.close();
        }
        try (PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql2)) {
            ps.setString(1, alter.getAuthor());
            ps.setString(2, alter.getPublisher());
            ps.setInt(3, alter.getReleaseYear());
            ps.setString(4, alter.getID());
            ps.executeUpdate();
            ps.close();
        }
        
        if (alter.getCoverFormat() != null) {
            FileHandle.uploadDocumentCover(alter.getID(), alter.getCover(), alter.getCoverFormat());
        }
        if (alter.getPDF() != null) {
            if (alter.getCover() == null) {
                    Image tempCover = FileHandle.getFirstPage(FileHandle.convertURL_File(alter.getPDF()));
                    alter.setCover(tempCover, "png");
                }
            FileHandle.uploadDocumentPDF(alter.getID(), alter.getPDF(), alter.getCover() == null);
        }
        
        return true;
    }
}
