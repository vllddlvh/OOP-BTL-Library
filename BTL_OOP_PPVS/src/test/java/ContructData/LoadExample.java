package ContructData;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import model.DatabaseConnector;
import model.dao.DocumentDAO;
import model.dao.FileFormatException;
import model.entity.Book;


/**
 *
 * @author Littl
 */
public class LoadExample {
    public static void main(String[] args) throws SQLException, IOException, FileFormatException, URISyntaxException {
        // Sau khi chạy AllInOne.sql để tạo database. Đến đoạn nạp đống ảnh của Vũ
        
        DatabaseConnector.getInstance();
        List<Book> list = DocumentDAO.getAllBook();
        for (Book x : list) {
            x.setCover(new File("src\\Ảnh mẫu ban đầu\\" + x.getID() + ".jpg"));
            System.out.println(x.getID());
            System.out.println(x.getTitle());
            System.out.println(x.getAuthor());
            System.out.println(x.getPublisher());
            System.out.println(x.getReleaseYear());
            System.out.println(x.getDescription());
            System.out.println(x.getCategory());
            System.out.println(x.getLanguage());
            
            DocumentDAO.updateBook(x);
                    
            
        }
        
        
    }
}
