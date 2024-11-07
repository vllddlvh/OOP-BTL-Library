package controller;

import java.sql.CallableStatement;
import java.awt.Desktop;
import java.sql.ResultSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;




public class UpdateFile extends DatabaseConnector {
    
    
    /**
     * Load a file for a book which already be created.
     * 
     * @param bookID = ID of the book whose this file.
     * @param file = BinaryFile as media src.
     * 
     * @return true if the file successfully uploaded.
     * 
     * @throws SQLException = throw if the 'loadNewBook' procedure is WRONG or be MISSING.
     * @throws IOException = throw if the File is null.
     */
    public static boolean uploadBookFileToDatabase(String bookID, File file) throws SQLException, IOException {
 
        CallableStatement loader = (CallableStatement) connection.prepareCall("{ call loadNewBook(?, ?) }");   
        
        // Set the book which get the file
        loader.setString(1, bookID);
 
        // Set the file as InputStream for the BLOB
        FileInputStream inputStream = new FileInputStream(file);
        loader.setBinaryStream(2, inputStream);
 
        // Execute the statement
        int row = loader.executeUpdate();
        if (row > 0) {
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Download and WRITE A COPY of the file you want from DATABASE.
     * 
     * @param bookID = ID of the book whose the file.
     * 
     * @return the PATH where contain the file temporary.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    private static File getBookFileFromDatabase(String bookID) throws SQLException, IOException {
        ResultSet rs;
        CallableStatement loader = (CallableStatement) connection.prepareCall("{ call downloadBook(?) }"); 
        
        loader.setString(1, bookID);
        
        rs = loader.executeQuery();
        
        while(rs.next()) {
            var fis = rs.getBinaryStream("PDF");
            if (fis == null) {
                return null;
            }
            
            // setup a place for the file to be download in
            FileOutputStream fos = new FileOutputStream("src/OuterData/" + bookID + ".pdf", false);
            
            // write the file on the place
            fos.write(fis.readAllBytes());
            
            fis.close();
            fos.close();
            
            // return the path contain the file.
            return new File("src/OuterData/" + bookID + ".pdf");
        }
        
        return null;
    }
    
    
    private static void openFile(File file) throws IOException {

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "File not found", "Error", 1);
        } else {
            Desktop.getDesktop().open(file);
        }
        
        /*
        Phương pháp hơi tà đạo, để nghiên cứu thêm.
        
        Process runFile = new ProcessBuilder(commandText).start();
        runFile.waitFor();
        */

    }
    
    
    /**
     * Save the book.pdf on the OuterData folder. 
     * 
     * @param bookID = ID of the book.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    public static void saveBookFile(String bookID) throws SQLException, IOException {
        openFile(getBookFileFromDatabase(bookID));
    }
    
    
    /**
     * Just open the book once. Then delete the file right away.
     * Note: if your pc have no default pdf opener, it may delete the file before you open it
     * 
     * @param bookID = ID of the book.
     * 
     * @throws SQLException
     * @throws IOException
     * @throws Exception 
     */
    public static void oneTimeOpenBook(String bookID) throws SQLException, IOException, Exception {
        File bookPDF = UpdateFile.getBookFileFromDatabase(bookID);
        UpdateFile.openFile(bookPDF);
        
        Thread.sleep(500); // Giữ file khoảng 0,5s rồi xóa. Không có default soft để open file thì chịu gòi.
        
        bookPDF.delete();
    }
}
