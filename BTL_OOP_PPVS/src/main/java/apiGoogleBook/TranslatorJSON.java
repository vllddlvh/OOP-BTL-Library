package apiGoogleBook;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.entity.Book;

/**
 *
 * @author FuK
 */
public class TranslatorJSON {
    public static LinkedList<Book> translateToBook(StringBuilder src) {
        if (src == null || src.isEmpty()) {
            System.out.println("Phản hồi JSON rỗng hoặc null, không thể phân tích.");
            return null;
        }
        
        
        LinkedList<Book> bookList = new LinkedList<>();
        try {
            JsonObject fullResult = new JsonParser().parse(src.toString()).getAsJsonObject();
            int n = fullResult.get("totalItems").getAsInt();
            if (n < 1) {
                System.out.println("Không tìm được kết quả phù hợp");
                return bookList;
            }
            
            JsonArray items = fullResult.get("items").getAsJsonArray();
            if (items == null) {
                System.out.println("Items mất tick");
                return bookList;
            }
           
            JsonObject currentItem = null;
            for (int i = 0; i < Math.min(5, n); i++) {
                currentItem = items.get(i).getAsJsonObject();
                JsonObject currentVolumeInfo = null;
                String id = null;
                String title = null;
                String author = "";
                String publisher = null;
                String releaseYear = null;
                String description = null;
                String category = null;
                String language = null;
                
                
                // Lấy thông tin cơ bản của sách.
                if (currentItem != null && currentItem.has("volumeInfo")) {
                    currentVolumeInfo = currentItem.get("volumeInfo").getAsJsonObject();
                }
                if (currentVolumeInfo == null) {
                    System.out.println("broken");
                    continue;
                }
                if (currentVolumeInfo.has("id")) {
                    id = currentItem.get("id").getAsString();
                }
                if (currentVolumeInfo.has("title")) {
                    title = currentVolumeInfo.get("title").getAsString();
                }
                if (currentVolumeInfo.has("authors")) {
                    JsonArray arr = currentVolumeInfo.get("authors").getAsJsonArray();
                    for (int j = arr.size() - 1; j >= 0; j--) {
                        author += ", " + arr.get(j).getAsString();
                    }
                    author = author.substring(2);
                }
                if (currentVolumeInfo.has("publisher")) {
                    publisher = currentVolumeInfo.get("publisher").getAsString();
                }
                if (currentVolumeInfo.has("publishedDate")) {
                    releaseYear = currentVolumeInfo.get("publishedDate").getAsString().substring(0, 4);
                }
                if (currentVolumeInfo.has("description")) {
                    description = currentVolumeInfo.get("description").getAsString();
                }
                if (currentVolumeInfo.has("language")) {
                    language = currentVolumeInfo.get("language").getAsString();
                }
                if (currentVolumeInfo.has("categories")) {
                    category = currentVolumeInfo.get("categories").getAsString();
                }
                
                // tạo form sách
                Book currentBook = new Book(id, 
                                                title, 
                                    100, 
                                                author, 
                                                publisher, 
                                       Integer.parseInt(releaseYear), 
                                                description, 
                                                category, 
                                                language);
                
                // lấy cover image URL
                if (currentVolumeInfo.has("imageLinks")) {
                    currentItem = currentVolumeInfo.get("imageLinks").getAsJsonObject();
                }
                if (currentVolumeInfo.has("thumbnail")) {
                    title = currentItem.get("thumbnail").getAsString(); // lưu tạm thumbnails
                    try {
                        System.out.println(title);
                        URL imageURL = new URL(title);

                        
                        // Chuyển URL sang ImageIcon cho sách
                        Image image = ImageIO.read(imageURL);
                        ImageIcon img = new ImageIcon(image);
                                
                        currentBook.setCover(img);
                    
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(TranslatorJSON.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Lỗi không tải được ảnh.\nLink: " + title);
                    } catch (IOException ex) {
                        Logger.getLogger(TranslatorJSON.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Lỗi không tải được ảnh.\nLink: " + title);
                    }
                }
                
                
                bookList.add(currentBook);
            }
            
            return bookList;
            
        } catch (JsonSyntaxException e) {
            Logger.getLogger(TranslatorJSON.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Lỗi khi phân tích JSON: " + e.getMessage());
        } 
        return null;
    }
    
    public static LinkedList<Book> translateToBook(File srcJSON) {
        StringBuilder storage = new StringBuilder();
        
        if(!srcJSON.exists() || !srcJSON.canRead()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(srcJSON))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    storage.append(line);
                    storage.append("\n");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TranslatorJSON.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TranslatorJSON.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return translateToBook(storage);
    }
}
