package model.dao;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.DatabaseConnector;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.tika.Tika;

/**
 *
 * @author Littl
 */
public class FileHandle {
    private final static Tika checking = new Tika(); // Để check định dạng của file.
    
    public static void openURL(URL url) throws IOException, URISyntaxException {
            Desktop.getDesktop().browse(url.toURI());
    }
    
    public static File convertURL_File(URL url) throws URISyntaxException {
        System.out.println(url.getProtocol());
        if (url.getProtocol().equals("file")) {
            try {
                String decodedPath = URLDecoder.decode(url.toURI().getPath(), "UTF-8");
                System.out.println(decodedPath);
                return new File(decodedPath);
                
            } catch (UnsupportedEncodingException ex) {
                System.out.println("Original URL: " + url.getPath());
                Logger.getLogger(FileHandle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static URL convertFile_URL(File file) throws MalformedURLException {
        if (file != null) {
            return file.toURI().toURL();
        }
        return null;
    }
    
    /**
     * Upload ảnh bìa cho tài liệu.
     * Chấp nhận file <b>png</b> hoặc <b>jpeg</b>.
     * Kích thước file không quá 64 KB
     * 
     * @param documentID = ID của tài liệu.
     * @param cover = File ảnh bìa.
     * @param format
     * 
     * @return true nếu upload thành công.
     * 
     * @throws IOException
     * @throws SQLException
     * @throws FileFormatException quá kích thước, hoặc sai định dạng.
     */
    public static boolean uploadDocumentCover(String documentID, Image cover, String format) throws IOException, 
                                                                                  SQLException, 
                                                                                  FileFormatException {
        
        // check ok rồi thì upload thôi.
        CallableStatement loader = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call loadDocumentCover(?, ?) }");   
        
        // Set the book which get the file
        loader.setString(1, documentID);
 
        
        BufferedImage bufferedImage = new BufferedImage(cover.getWidth(null), cover.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(cover, 0, 0, null);
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        if (format == null) {
            format = "jpg";
        }
        ImageIO.write(bufferedImage, format, output);
        
        loader.setBytes(2, output.toByteArray());
 
        // Execute the statement ả
        int row = loader.executeUpdate();
        return row > 0;
    }
    
    /**
     * Tải bìa tài liệu về từ Database. 
     * Trả về hình kiểu jpeg hoặc png. Lưu vào OuterData
     * 
     * @param documentID
     * 
     * @return
     * 
     * @throws IOException
     * @throws SQLException 
     */
    public static Image getDocumentCover(String documentID) throws IOException, SQLException {
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
                    return reader;
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
    public static boolean uploadDocumentPDF(String documentID, URL PDF, boolean firstPageBeingCover) throws IOException, 
                                                                                                            SQLException, 
                                                                                                            FileFormatException,
                                                                                                            URISyntaxException {
        File pdf = convertURL_File(PDF);
        
        // Nếu đây là file tải sẵn về từ db (path là thư mục gốc), thì bỏ qua không upload
        if (pdf == null || pdf.getPath().equals("src/OuterData/" + documentID + ".pdf")) {
            return false;
        }
        
        if (firstPageBeingCover) {
            uploadDocumentCover(documentID, getFirstPage(documentID, pdf), "PNG");
        }
        
        CallableStatement loader = (CallableStatement) DatabaseConnector.getConnection().prepareCall("{ call loadDocumentPDF(?, ?) }");   
        
        // Set the book which get the file
        loader.setString(1, documentID);
 
        // Set the file as InputStream for the BLOB
        FileInputStream is = new FileInputStream(pdf);
        loader.setBinaryStream(2, is);
 
        // Execute the statement
        int row = loader.executeUpdate();
        return row > 0;
    }
    
    public static Image getFirstPage(String documentID, File pdf) throws FileFormatException, IOException, SQLException {
        PDDocument document = PDDocument.load(pdf);
        if (document.getNumberOfPages() > 0) {
            File cover = new File("src\\OuterData\\temp.bin");
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 50); // Trang đầu tiên, DPI = 300
            
            cover.delete();
            return image;
            
        } else {
            throw new FileFormatException("File trống. Vui lòng thử lại");
        }
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
        File result = new File("src/OuterData/" + documentID + ".pdf");
        if (result.exists()) {
            return result;
        }
        
        String sql = "SELECT PDF FROM library_2nd_edition.storedDocument WHERE ID = ?";
        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(sql);
        
        ps.setString(1, documentID);
        ResultSet rs = ps.executeQuery();
        
        
        while(rs.next()) {
            
            FileOutputStream fos;
            try (java.io.InputStream fis = rs.getBinaryStream(1)) {
                if (fis == null) {
                    return null;
                }   // setup a place for the file to be download in
                fos = new FileOutputStream("src/OuterData/" + documentID + ".pdf", false);
                // write the file on the place
                fos.write(fis.readAllBytes());
            }
            fos.close();
            ps.close();
            rs.close();
            
            // return the path contain the file.
            return result;
        }
        
        return null;
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
        String mimeType = checking.detect(pdf);
        if (!mimeType.equals("application/pdf")) {
            throw new FileFormatException("Yêu cầu file định dạng PDF");
        }
        
        return true;
    }
}
