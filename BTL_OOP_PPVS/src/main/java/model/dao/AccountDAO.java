/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DatabaseConnector;
import model.entity.Account;

/**
 *
 * @author HP
 */
public class AccountDAO {
    public static Account Login(String username, String password) throws SQLException {
        Connection cons = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM login WHERE ID = ? AND password = ?";
        Account account = null;
        try (PreparedStatement ps = cons.prepareStatement(sql)) { // Sử dụng try-with-resources để đóng tự động
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setUsername(rs.getString("ID"));
                account.setPassword(rs.getString("password"));
                account.setRole(rs.getString("Role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Ném lại để hiển thị lỗi trong phần View
        }
        return account;
    }
}
