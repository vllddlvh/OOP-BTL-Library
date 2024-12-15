package model;


import java.sql.DriverManager;
import java.sql.Connection;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;


public class DatabaseConnector {
    
    private static Connection connection = null;
    private final static String url = "jdbc:mysql://127.0.0.1:3306/Library_2nd_Edition";
    private final static String username = "root";
    private final static String password = "";
    
    /**
     * Tạo connection lần đầu.
     * 
     * @throws SQLException 
     */
    public static void getInstance() throws SQLException {
        new DatabaseConnector();
    }
    
    /**
     * Lấy connection với cơ sở dữ liệu đã được tạo dựng sẵn.
     * Xây dựng theo pattern Singleton nên sẽ chỉ duy trì 1 connection duy nhất.
     * Không thể tạo thêm Connector. 
     * 
     * @return
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            getInstance();
        }
        return connection;
    }
    
    /**
     * Đóng connection tới cơ sở dữ liệu sau khi đã hoàn thành xong các truy vấn.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Close Connection");
        }
    }
    
    /**
     * Contructor. Tạo ra connection. Lỗi sẽ thông báo lên terminal.
     * 
     * @throws SQLException 
     */
    private DatabaseConnector() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        if (connection != null ) {
            String[] lib = DatabaseConnector.url.split("/");
            System.out.print("\n\n\n\n\n\n\n\nConnecting Database Success\n");
            System.out.println(lib[3] + "\n\n");
        } else {
            System.out.print("\n\n\n\n\n\n\n\nConnecting Database FAILED\n");
        }
    }
}

