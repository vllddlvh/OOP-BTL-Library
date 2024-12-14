package model.entity;



import java.awt.Graphics2D;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.dao.DocumentDAO;
import model.dao.FileFormatException;
import model.dao.FileHandle;

/**
 *
 * @author Littl
 */
public abstract class Document {
    protected String ID;
    protected String title;
    protected int availableCopies = 100;
    protected ArrayList<String> category = new ArrayList<>();
    protected String description;
    protected String language = "Không rõ";
    
    protected Image cover = null;
    protected URL PDF = null;
    
    // Chỉ sử dụng nội trong model. 
    // Tránh down lại file nhiều lần, và xác định định dạng ảnh. 
    protected boolean haveCover = true;
    protected boolean havePDF = true;
    protected String coverFormat = null;
    
    /**
     * Get document search by title. 
     * Book title, or Thesis title and Field of Study.
     * 
     * @param titleKeyword = search keyword
     * 
     * @return list of Document (both Thesis and Book)
     * 
     * @throws SQLException 
     * @throws IOException
     */
    public static ArrayList<Document> searchDocument(String titleKeyword) throws SQLException, IOException {
        ArrayList<Document> result = new ArrayList<>();
        
        result.addAll(Book.searchBook(titleKeyword, "", 0, null, null));
        
        return result;
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
        return DocumentDAO.getDocumentInfo(documentID);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        StringBuilder result = new StringBuilder();
        for (String x : this.category) {
            result.append(", ").append(x);
        }
        if (result.length() < 2) {
            return "";
        }
        return result.substring(2);
    }

    public void setCategory(ArrayList<String> category) {
        for (String x : category) {
            this.category.add(x.trim());
        }
    }
    
    public void setCategory(String category) {
        this.category = new ArrayList<>();
        String[] arr = category.split("[,.]+");
        for (String x : arr) {
            this.category.add(x.trim());
        }
    }
    
    public int getCategoryEncrypt() throws IOException {
        return CategoryType.encrypt(this.category);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getCoverFormat() {
        return coverFormat;
    }

    public Image getCover() throws IOException, SQLException, FileFormatException  {
        if (cover == null && haveCover) {
            cover = FileHandle.getDocumentCover(ID);
            haveCover = false;
        }
        return cover;
    }

    public void setCover(File cover) throws FileFormatException, IOException {
        if (cover == null) {
            this.cover = null;
            return;
        }
        
        coverFormat = FileHandle.checkFileCover(cover);
        
        // Ảnh gốc
        this.cover = ImageIO.read(cover);
        
        // Vẽ lại toàn bộ ảnh, thay đổi kích thước, giảm resolution
        BufferedImage resizedImage = new BufferedImage(300, 375, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(this.cover, 0, 0, 300, 375, null);
        g2d.dispose();
        
        // gán ảnh sau khi đã giảm độ phân giải
        this.cover = resizedImage;
        
        haveCover = true;
    }
        
    public void setCover(Image cover, String coverFormat) {
        this.coverFormat = coverFormat;
        this.cover = cover;
        haveCover = true;
    }

    public URL getPDF() throws IOException, SQLException {
        if (PDF == null && havePDF) {
            File file = FileHandle.getDocumentPDF(ID);
            PDF = model.dao.FileHandle.convertFile_URL(file);
            havePDF = false;
        }
        return PDF;
    }

    public void setPDF(File PDF) throws IOException, FileFormatException, SQLException {   
        FileHandle.checkFilePDF(PDF);
        
        if (cover == null || !haveCover) {
            setCover(FileHandle.getFirstPage(ID, PDF), "png");
            haveCover = false;
        }
        
        this.PDF = model.dao.FileHandle.convertFile_URL(PDF);
        havePDF = true;
    }
    
    public void setPDF(URL url) {
        this.PDF = url;
        havePDF = true;
    }
    
    public boolean openSamplePDF() throws IOException, SQLException, URISyntaxException {
        if (PDF == null) {
            getPDF();
        }
        if (PDF == null) {
            return false;
        }
        
        try {
            FileHandle.openURL(this.PDF);
            Thread.sleep(500);
            FileHandle.convertURL_File(PDF).delete();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public boolean openFullPDF() throws IOException, SQLException, URISyntaxException {
        if (PDF == null) {
            getPDF();
        }
        if (PDF == null) {
            return false;
        }
        
        FileHandle.openURL(this.PDF);
        return true;
    }
    
    public static class CategoryType {
        private static ArrayList<String> decoder;
        private static HashMap<String, Integer> encrypter;
        private final static String PATH = "src\\main\\java\\image\\Document Category.txt";
        
        private static void loadDecoder() throws IOException {
            decoder = new ArrayList<>();
            encrypter = new HashMap<>();
            
            FileReader input = new FileReader(PATH);
            BufferedReader reader = new BufferedReader(input);
 
            int power = 1;
            String line = reader.readLine();
            while (line != null) {
                decoder.add(line);
                encrypter.put(line, power);
                power *= 2;
                
                line = reader.readLine();
            }
            
            reader.close();
        }
        
        static ArrayList<String> decode(int k) throws IOException {
            if (decoder == null) {
                loadDecoder();
            }
            
            ArrayList<String> result = new ArrayList<>();
            int i = 0;
            while (k > 0) {
                if (k % 2 == 1) {
                    result.add(decoder.get(i));
                }
                k /= 2;
                i++;
            }
            return result;
        }
        
        static void addNewCategory (List<String> newCategory) throws IOException {
            FileWriter output = new FileWriter(PATH, true);
            BufferedWriter writer = new BufferedWriter(output);
            
            int power = (int) Math.pow(2, decoder.size());
            for (String x : newCategory) {
                writer.newLine();
                writer.write(x);
                
                decoder.add(x);
                encrypter.put(x, power);
                power *= 2;
            }
            
            writer.close();
            
            
        } 
        
        static int encrypt(List<String> category) throws IOException {
            if (encrypter == null) {
                loadDecoder();
            }
            
            int result = 0;
            int singleCategory;
            ArrayList<String> newCate = new ArrayList<>(); 
            for (String x : category) {
                singleCategory = encrypter.getOrDefault(x, 0);
                if (singleCategory == 0) {
                    newCate.add(x);
                } else {
                    result += singleCategory;
                }
            }
            addNewCategory(newCate);
            for (String x : newCate) {
                result += encrypter.getOrDefault(x, 0);
            }
            
            return result;
        }
    }
    
}
