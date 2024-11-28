package apiGoogleBook;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Book;

/**
 *
 * @author FuK
 */
public class APIConnector {
    private static final String API_KEY = "AIzaSyB5dvT2OSJZxqpMKgS7gEw-5GN_uKpQAPs";
    
    public static LinkedList<Book> searchBook(String keyword) {
        try {
            StringBuilder query = new StringBuilder("q=");
            query.append(keyword.replace(" ", "+"));
            query.append("&key=" + API_KEY);

            // Tạo URL cho truy vấn tìm kiếm
            String urlString = "https://www.googleapis.com/books/v1/volumes?" + query.toString();
            System.out.println("Request URL: " + urlString);

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
            StringBuilder response = new StringBuilder();

            // Copy y nguyên những gì API trả về
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\n");
            }
            writer.write(response.toString());
            reader.close();
            connection.disconnect();

            LinkedList<Book> result = TranslatorJSON.translateToBook(response);
            System.out.println("done"  + result.size());
            return result;
                    
        } catch (Exception e) {
            Logger.getLogger(APIConnector.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Lỗi khi tìm kiếm tài liệu: " + e.getMessage());
        }
        return null;
    }
}
