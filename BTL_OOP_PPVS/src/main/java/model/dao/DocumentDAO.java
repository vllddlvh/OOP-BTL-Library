package model.dao;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.tika.Tika;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import model.DatabaseConnector;
import model.entity.Book;
import model.entity.Document;



/**
 *
 * @author Littl
 */
public abstract class DocumentDAO {
    private final static Tika checking = new Tika(); // Để check định dạng của file.
    
    // Test nhập xuất file
    public static void main(String[] args) {
        File pdf = new File("src\\OuterData\\Phát triển ứng dụng quản lý thư viện bằng Java.pdf");
        
        try {
            checkFilePDF(pdf);
            
            //uploadDocumentCover("T001", cover);
            uploadDocumentPDF("T001", pdf, true);
            // Cover lấy luôn trang đầu.
            
            getDocumentCover("T001");
            getDocumentPDF("T001");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
    /**
     * Upload ảnh bìa cho tài liệu.
     * Chấp nhận file <b>png</b> hoặc <b>jpeg</b>.
     * Kích thước file không quá 64 KB
     * 
     * @param documentID = ID của tài liệu.
     * @param cover = File ảnh bìa.
     * 
     * @return true nếu upload thành công.
     * 
     * @throws IOException
     * @throws SQLException
     * @throws FileFormatException quá kích thước, hoặc sai định dạng.
     */
    public static boolean uploadDocumentCover(String documentID, ImageIcon cover, String format) throws IOException, 
                                                                                  SQLException, 
                                                                                  FileFormatException {
        
        // check ok rồi thì upload thôi.
        CallableStatement loader = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call loadDocumentCover(?, ?) }");   
        
        // Set the book which get the file
        loader.setString(1, documentID);
 
        
        Image image = cover.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, output);
        
        loader.setBytes(2, output.toByteArray());
 
        // Execute the statement ả
        int row = loader.executeUpdate();
        if (row > 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Tải bìa tài liệu về từ Database. 
     * Trả về hình kiểu jpeg hoặc png. Lưu vào OuterData
     * 
     * @param documentID
     * @return
     * 
     * @throws IOException
     * @throws SQLException 
     */
    public static ImageIcon getDocumentCover(String documentID) throws IOException, SQLException {
        String sql = "SELECT cover FROM library_2nd_edition.storedDocument WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        
        ps.setString(1, documentID);
        ResultSet rs = ps.executeQuery();
        
        
        while(rs.next()) {
            byte[] imageBytes = rs.getBytes(1);
            
            var fis = rs.getBinaryStream(1);
            if (fis == null) {
                return null;
            }
            
            if (imageBytes != null) {
                ByteArrayInputStream byteInput = new ByteArrayInputStream(imageBytes);
                BufferedImage reader = ImageIO.read(byteInput);
                if (reader != null) {
                    return new ImageIcon(reader);
                }
            }
            
            return null;
        }
        
        return null;
    }
    
    /**
     * Upload file pdf cho tài liệu.
     * Đống thời sẽ lấy luôn trang đầu tiên làm Cover.
     * 
     * @param documentID = ID tài liệu.
     * @param pdf = file cần upload.
     * @param firstPageBeingCover = chọn xem có lấy trang đầu là cover không.
     * 
     * @return true nếu upload thành công.
     * 
     * @throws IOException
     * @throws SQLException 
     * @throws model.dao.FileFormatException 
     */
    public static boolean uploadDocumentPDF(String documentID, File pdf, boolean firstPageBeingCover) throws IOException, 
                                                                                                            SQLException, 
                                                                                                            FileFormatException {
        
        checkFilePDF(pdf);
        
        PDDocument document = PDDocument.load(pdf);
        if (document.getNumberOfPages() > 0) {
            if (firstPageBeingCover) {
                File cover = new File("src\\OuterData\\temp.bin");
                PDFRenderer renderer = new PDFRenderer(document);
                BufferedImage image = renderer.renderImageWithDPI(0, 50); // Trang đầu tiên, DPI = 300
                ImageIO.write(image, "PNG", cover);
            
                uploadDocumentCover(documentID, new ImageIcon(cover.getAbsolutePath()), "PNG");
                cover.delete();
            }
            
        } else {
            throw new FileFormatException("File trống. Vui lòng thử lại");
        }
        
        
        
        CallableStatement loader = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call loadDocumentPDF(?, ?) }");   
        
        // Set the book which get the file
        loader.setString(1, documentID);
 
        // Set the file as InputStream for the BLOB
        FileInputStream is = new FileInputStream(pdf);
        loader.setBinaryStream(2, is);
 
        // Execute the statement
        int row = loader.executeUpdate();
        if (row > 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Lấy file PDF của tài liệu được lưu. Lưu ý là chỉ PDF thôi nhá.
     * Nếu tài liệu chưa chưa file PDF hoặc sai documentID thì trả về null.
     * 
     * @param documentID = ID tài liệu.
     * 
     * @return File (đã được tải về máy) pdf của tài liệu.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    public static File getDocumentPDF(String documentID) throws IOException, SQLException {
        String sql = "SELECT PDF FROM library_2nd_edition.storedDocument WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        
        ps.setString(1, documentID);
        ResultSet rs = ps.executeQuery();
        
        
        while(rs.next()) {
            
            var fis = rs.getBinaryStream(1);
            if (fis == null) {
                return null;
            }
            
            // setup a place for the file to be download in
            FileOutputStream fos = new FileOutputStream("src/OuterData/" + documentID + ".pdf", false);
            
            // write the file on the place
            fos.write(fis.readAllBytes());
            
            fis.close();
            fos.close();
            ps.close();
            rs.close();
            
            // return the path contain the file.
            return new File("src/OuterData/" + documentID + ".pdf");
        }
        
        return null;
    }
    
    
    
    /**
     * Get Document only if you know exactly its Thesis ID / Book ISBN.
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
     * Load more or less copies of a document already store in library.
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
     * Delete a document determine by its ID.
     * 
     * @param documentID = ID of the document want to be deleted.
     * 
     * @throws SQLException 
     */
    public static void deleteDocument(String documentID) throws SQLException  {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call deleteDocument(?) }");
        
        finder.setString(1, documentID);
        
        finder.executeQuery();
    }
    
    
    /**
     * Lấy toàn bộ Book trên database.
     * 
     * @return = ?
     * 
     * @throws SQLException
     * @throws IOException 
     */
    public static LinkedList<Book> getAllBook() throws SQLException, IOException {
        String sql = """
                     SELECT
                     \t\tbooks.ISBN AS ISBN,
                     \t\tdocuments.title AS title,
                     \t\tdocuments.quantityLeft AS quantityAvailable,
                     \t\tbooks.author AS author,
                     \t\tbooks.publisher AS publisher,
                     \t\tbooks.releaseYear AS releaseYear,
                     \t\tdocuments.Description AS Description,
                     \t\tdocuments.category AS category,
                     \t\tdocuments.language AS language
                     \tFROM books left join documents ON (books.isbn = documents.ID)""";
        ResultSet rs;
        LinkedList<Book> list;
        try (PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql)) {
            rs = ps.executeQuery();
            list = new LinkedList<>();
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
     * Search Book with given keyword types below.
     * 
     * @param titleKeyword = searching by %title%.
     * @param authorKeyword = searching by %author%.
     * @param releaseYear = search by releaseYear. Sorry, not search on range of Years.
     * @param category = on format of ENUM Book.BookCategory.
     * @param language = book's language.
     * 
     * @return List of books found.
     * @throws SQLException 
     * @throws java.io.IOException 
     */
    public static ArrayList<Book> searchBook(String titleKeyword, String authorKeyword, int releaseYear, int category, String language) throws SQLException, IOException {
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call searchBook(?, ?, ?, ?, ?) }");
        
        finder.setString(1, titleKeyword);
        finder.setString(2, authorKeyword);
        finder.setInt(3, releaseYear);
        finder.setInt(4, category);
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
     * Add a new Book to database. 
     * 
     * @param newBook = only Book object have been full construct accept.
     * 
     * @return false if duplicate documentID.
     * 
     * @throws SQLException 
     * @throws IOException
     */
    public static boolean addBook(Book newBook) throws SQLException, IOException, FileFormatException  {
        
        CallableStatement finder = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call addBook(?, ?, ?, ?, ?, ?, ?, ?) }");
        
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
            if (newBook.getCover() != null) {
                uploadDocumentCover(newBook.getID(), newBook.getCover(), newBook.getCoverFormat());
            }
            if (newBook.getPDF() != null) {
                uploadDocumentPDF(newBook.getID(), newBook.getPDF(), newBook.getCover() == null);
            }
            return rs.getBoolean(1);
        }
        
        return false;
    }
    
    /**
     * Chỉnh sửa thông tin cho một quyển sách.
     * 
     * @param alter = kết quả cần đạt được sau chỉnh sửa.
     * 
     * @return true nếu sửa thành công.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    public static boolean updateBook(Book alter) throws SQLException, IOException, FileFormatException {
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
        
        
        return uploadDocumentCover(alter.getID(), alter.getCover(), alter.getCoverFormat());
    }
    
    /**
     * Check xem file vào có phải đúng là ảnh không.
     * Vì không lưu coverImage dưới dạng file đóng gói.
     * Mà ở dạng byte[] trong cache. Nên không cần lo về định dạng khi đọc.
     * Chỉ là cần cho khi viết lên database.
     * <p>
     * Nếu không thì ném lỗi FileFormatEx
     * 
     * @param image = file ảnh.
     * 
     * @return imageFormat
     * 
     * @throws FileFormatException
     * @throws IOException 
     */
    public static String checkFileCover(File image) throws FileFormatException, IOException {
        if (!image.exists()) {
            throw new FileFormatException("File không tồn tại\n" + image.getAbsolutePath());
        }
        if (!image.canRead()) {
            throw new FileFormatException("Hiện không thể đọc file tại đường dẫn này\n" + image.getAbsolutePath());
        }

        // Check kích thước file nếu quá lớn.
        if (image.length() > 65535) {
            throw new FileFormatException("File quá lớn. Hãy đảm bảo dung lượng dưới 64KB\n" + image.getAbsolutePath());
        }
        
        // Check định dạng file ảnh. Chỉ nhận file png hoặc jpeg.
        String mimeType = checking.detect(image);
        if (!mimeType.startsWith("image/")) {
            // Định dạng file khác.
            throw new FileFormatException("File này không phải hình ảnh. Vui lòng thử lại\n" + image.getAbsolutePath());
        }
        return mimeType.split("/")[1];
    }
    
    public static boolean checkFilePDF(File pdf) throws FileFormatException, IOException {
        if (!pdf.exists()) {
            throw new FileFormatException("File không tồn tại\n" + pdf.getAbsolutePath());
        }
        if (!pdf.canRead()) {
            throw new FileFormatException("Hiện không thể đọc file tại đường dẫn này\n" + pdf.getAbsolutePath());
        }
        // check kích thước
        if (pdf.length() > 16777215) {
            throw new FileFormatException("File quá lớn. Hãy đảm bảo dung lượng dưới 16MB\n" + pdf.getAbsolutePath());
        }
        
        // check định dạng. Tuy nhiên ko có prefix, nên cam chịu vậy.
        Tika checking = new Tika();
        String mimeType = checking.detect(pdf);
        if (!mimeType.equals("application/pdf")) {
            throw new FileFormatException("Yêu cầu file định dạng PDF");
        }
        
        return true;
    }

}
