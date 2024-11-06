package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

    private static final String URL = "jdbc:mysql://localhost:3306/testjv";
    private static final String USER = "root";
    private static final String PASSWORD = "Daolelongvu2005@";

    public DAO() {
        try {
            // Khởi tạo driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Thử kết nối với cơ sở dữ liệu
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                System.out.println("Kết nối thành công!");
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new DAO(); // Kiểm tra kết nối khi chạy
    }
}
