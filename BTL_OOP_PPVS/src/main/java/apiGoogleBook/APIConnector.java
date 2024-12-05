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
import java.sql.SQLException;
import java.util.LinkedList;
  
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import model.dao.FileFormatException;
import model.entity.Book;

/**
 * Lớp APIConnector kết nối với Google Books API để tìm kiếm và xử lý thông tin sách.
 */
public class APIConnector {
    private static final String API_KEY = "AIzaSyB5dvT2OSJZxqpMKgS7gEw-5GN_uKpQAPs";

    private static final int MAX_RESULTS = 5; // Số lượng kết quả tối đa

    /**
     * Tìm kiếm sách trên Google Books API bằng từ khóa.
     *
     * @param keyword Từ khóa tìm kiếm.
     * @return Danh sách các đối tượng Book từ kết quả tìm kiếm.
     */
    public static LinkedList<Book> searchBook(String keyword) {
        try {
            // Xây dựng chuỗi truy vấn từ từ khóa
    private static final int MAX_RESULTS = 10;
    
    public static void searchBook(String keyword, List<Book> storedDocument) {
        try {
            // Chuẩn hóa keyword để tạo URL tìm kiếm
            StringBuilder query = new StringBuilder("q=");
            query.append(keyword.replace(" ", "+"));
            query.append("&maxResults=").append(MAX_RESULTS);
            query.append("&key=" + API_KEY);

            // Tạo URL truy vấn
            String urlString = "https://www.googleapis.com/books/v1/volumes?" + query.toString();
            System.out.println("Tìm kiếm: " + urlString + "\nLink ảnh:\n");


            // Kết nối với Google Books API

            
            // Tạo connection với API
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Kiểm tra phản hồi HTTP
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) { // Không thành công
                System.out.println("Lỗi khi kết nối API: Mã phản hồi HTTP " + responseCode);
            }


            // Đọc dữ liệu JSON trả về từ API

            // Chuẩn bị file lưu kết quả tra cứu
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\java\\apiGoogleBook\\tracuu.json"));
            String line;

            // Sao chép dữ liệu JSON trả về vào file
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            // Đóng tài nguyên
            reader.close();
            writer.close();
            connection.disconnect();


            // Phân tích file JSON
            File result = new File("src\\main\\java\\apiGoogleBook\\tracuu.json");
            return jsonTranslate(result);


            
            // Tạo đường dẫn file chứa kết quả tra cứu API
            File result = new File("src\\main\\java\\apiGoogleBook\\tracuu.json");
            
            // gửi File đi phân tích
            jsonTranslate(result, storedDocument);
                    

        } catch (IOException e) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Lỗi khi tìm kiếm tài liệu: " + e.getMessage());
        }
    }


    /**
     * Phân tích file JSON trả về từ API để tạo danh sách sách.
     *
     * @param src File JSON trả về từ API.
     * @return Danh sách các đối tượng Book từ dữ liệu JSON.
     */
    public static LinkedList<Book> jsonTranslate(File src) {

    
    public static void jsonTranslate (File src, List<Book> storedDocument) {

        if (!src.exists() || !src.canRead()) {
            System.out.println("Phản hồi JSON rỗng hoặc null, không thể phân tích.");
        }


        LinkedList<Book> bookList = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            JsonObject fullResult = new JsonParser().parse(reader).getAsJsonObject();

            // Kiểm tra số lượng sách tìm được

   
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            JsonObject fullResult = new JsonParser().parse(reader).getAsJsonObject(); // Đối tượng trong json
            
            // Nếu không có danh sách Book thì hủy

            int n = fullResult.get("totalItems").getAsInt();
            if (n < 1) {
                System.out.println("Không tìm được kết quả phù hợp");
            }


            // Phân tích từng sách trong danh sách items
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

                // Lấy thông tin cơ bản của sách
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

                // Tạo đối tượng sách
                Book currentBook = new Book(id, title, 100, author, publisher, Integer.parseInt(releaseYear), description, category, language);

                // Lấy URL ảnh bìa
                if (currentVolumeInfo.has("imageLinks")) {
                    currentItem = currentVolumeInfo.get("imageLinks").getAsJsonObject();
                }
                if (currentItem.has("thumbnail")) {
                    title = currentItem.get("thumbnail").getAsString(); // Lưu URL ảnh bìa
                    try {
                        System.out.println(title);
                        URL imageURL = new URL(title);

                        // Đọc ảnh từ URL và lưu vào đối tượng sách
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

                // Thêm sách vào danh sách
                bookList.add(currentBook);
            }

            return bookList;

        } catch (JsonSyntaxException | FileNotFoundException e) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Lỗi khi phân tích JSON: " + e.getMessage());
        }
        return bookList;

            
            // Lấy dãy items == Book
            JsonArray items = fullResult.get("items").getAsJsonArray();
            if (items == null) {
                System.out.println("Items mất tick");
            }
            
            // Phiên dịch thông tin cơ bản
            for (int i = 0; i < Math.min(MAX_RESULTS, n); i++) {
                
                // Đẩy item vào Thread dịch thông tin
                makeTranslateTask(items.get(i).getAsJsonObject(), storedDocument);
                
            }
            
        } catch (JsonSyntaxException | FileNotFoundException e) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Lỗi khi phân tích JSON: " + e.getMessage());
        } 
    }
    
    private static void makeTranslateTask(JsonObject currentItem, List<Book> storedDocument) {
        Runnable getTask = new Runnable() {
            @Override
            public void run() {
                translateSingleItem(currentItem, storedDocument);
            }
        };
        service.ThreadPool.addSetterTask(getTask);
    }
    
    private static void translateSingleItem(JsonObject currentItem, List<Book> storedDocument) {
        String id = null;
        String title = "";
        String author = "";
        String publisher = "";
        String releaseYear = "0000";
        String description = "";
        String category = "";
        String language = "";
        JsonObject currentVolumeInfo = null; // Để lấy ảnh
        
        // Lấy trước VolumeInfo. Nhưng đến bước cuối mới lấy ảnh
        if (currentItem != null && currentItem.has("volumeInfo")) {
            currentVolumeInfo = currentItem.get("volumeInfo").getAsJsonObject();
        }
        if (currentVolumeInfo == null) {
            System.out.println("broken");
            return;
        }
        
        
        // Lấy thông tin cơ bản của sách.
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
        storedDocument.add(currentBook);
        
        // Đẩy việc render cho thread Render
        makeSetCoverTask(currentBook, currentVolumeInfo);
    }
    
    public static void makeSetCoverTask(Book currentBook, JsonObject currentVolumeInfo) {
        service.ThreadPool.addGetterTask(new Runnable() {
            @Override
            public void run() {
                setBookCover(currentBook, currentVolumeInfo);
            }
        });
    }
    
    private static void setBookCover(Book currentBook, JsonObject currentVolumeInfo) {
        JsonObject thumbnail = null;
        String link;
        
        // Lấy thông tin bìa sách
        if (!currentVolumeInfo.has("imageLinks")) {
            return;
        }
        thumbnail = currentVolumeInfo.get("imageLinks").getAsJsonObject();
        if (!thumbnail.has("thumbnail")) {
            return;
        }
        link = thumbnail.get("thumbnail").getAsString(); // lưu tạm thumbnails
        try {
            System.out.println(link);
            URL imageURL = new URL(link);

                        
            // Chuyển URL sang ImageIcon cho sách
            Image image = ImageIO.read(imageURL);
                                
            currentBook.setCover(image, "jpg");
                    
        } catch (MalformedURLException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lỗi không tải được ảnh.\nLink: " + link);
        } catch (IOException ex) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lỗi không tải được ảnh.\nLink: " + link);
        }
        
        makeRenderTask(currentBook);
        
    }
    
    private static void makeRenderTask(Book document) {
        service.ThreadPool.addRenderTask(new Runnable() {
            @Override
            public void run() {
                try {
                    view.TimKiemCungAPI.newest.displaySingleDocument(document);
                } catch (IOException | SQLException | FileFormatException ex) {
                    Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(view.TimKiemCungAPI.newest, ex.getMessage());
                }
            }
        });

    }
}
