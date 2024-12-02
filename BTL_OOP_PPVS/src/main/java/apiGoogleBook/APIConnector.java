package apiGoogleBook;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.entity.Book;

/**
 *
 * @author FuK
 */
public class APIConnector {
    private static final String API_KEY = "AIzaSyB5dvT2OSJZxqpMKgS7gEw-5GN_uKpQAPs";
    private static final int MAX_RESULTS = 5;
    
    public static LinkedList<Book> searchBook(String keyword) {
        try {
            StringBuilder query = new StringBuilder("q=");
            query.append(keyword.replace(" ", "+"));
            query.append("&maxResults=" + MAX_RESULTS);
            query.append("&key=" + API_KEY);
            

            // Tạo URL cho truy vấn tìm kiếm
            String urlString = "https://www.googleapis.com/books/v1/volumes?" + query.toString();
            System.out.println("Tìm kiếm: " + urlString + "\nLink ảnh:\n");
            

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Kiểm tra mã phản hồi HTTP
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) { // Không thành công
                System.out.println("Lỗi khi kết nối API: Mã phản hồi HTTP " + responseCode);
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\apiGoogleBook\\tracuu.json"));
            String line;

            // Copy y nguyên những gì API trả về
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            
            // Đóng kết nối an toàn.
            reader.close();
            writer.close();
            connection.disconnect();

            File result = new File("src\\main\\java\\apiGoogleBook\\tracuu.json");
            
            // gửi File đi phân tích
            return jsonTranslate(result);
                    
        } catch (IOException e) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Lỗi khi tìm kiếm tài liệu: " + e.getMessage());
        }
        return null;
    }
    
    public static LinkedList<Book> jsonTranslate (File src) {
        if (!src.exists() || !src.canRead()) {
            System.out.println("Phản hồi JSON rỗng hoặc null, không thể phân tích.");
            return null;
        }
        
        
        LinkedList<Book> bookList = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            JsonObject fullResult = new JsonParser().parse(reader).getAsJsonObject();
            
            
            
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
            for (int i = 0; i < Math.min(MAX_RESULTS, n); i++) {
                currentItem = items.get(i).getAsJsonObject();
                JsonObject currentVolumeInfo = null;
                String id = null;
                String title = "";
                String author = "";
                String publisher = "";
                String releaseYear = "0000";
                String description = "";
                String category = "";
                String language = "";
                
                
                // Lấy thông tin cơ bản của sách.
                if (currentItem != null && currentItem.has("volumeInfo")) {
                    currentVolumeInfo = currentItem.get("volumeInfo").getAsJsonObject();
                }
                if (currentVolumeInfo == null) {
                    System.out.println("broken");
                    continue;
                }
                if (currentItem.has("id")) {
                    id = currentItem.get("id").getAsString();
                }
                if (currentVolumeInfo.has("title")) {
                    title = currentVolumeInfo.get("title").getAsString();
                }
                if (currentVolumeInfo.has("authors")) {
                    JsonArray arr = currentVolumeInfo.get("authors").getAsJsonArray();
                    if (arr != null && arr.size() > 0) {
                        for (int j = arr.size() - 1; j >= 0; j--) {
                            author += ", " + arr.get(j).getAsString();
                        }
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
                if (currentItem.has("thumbnail")) {
                    title = currentItem.get("thumbnail").getAsString(); // lưu tạm thumbnails
                    try {
                        System.out.println(title);
                        URL imageURL = new URL(title);

                        
                        // Chuyển URL sang ImageIcon cho sách
                        Image image = ImageIO.read(imageURL);
                                
                        currentBook.setCover(image, "jpg");
                    
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Lỗi không tải được ảnh.\nLink: " + title);
                    } catch (IOException ex) {
                        Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Lỗi không tải được ảnh.\nLink: " + title);
                    }
                }
                
                
                bookList.add(currentBook);
            }
            
            return bookList;
            
        } catch (JsonSyntaxException | FileNotFoundException e) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Lỗi khi phân tích JSON: " + e.getMessage());
        } 
        return bookList;
    }
}
