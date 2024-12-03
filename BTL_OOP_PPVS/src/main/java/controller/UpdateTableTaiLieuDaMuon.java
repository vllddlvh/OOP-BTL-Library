/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.dao.FileFormatException;
import model.dao.RequestDAO;
import model.entity.Request;

/**
 *
 * @author ADMIN
 */
public class UpdateTableTaiLieuDaMuon {
    private static ArrayList<Request> taiLieuDaMuon = new ArrayList<>();
    
    public UpdateTableTaiLieuDaMuon() throws SQLException {
        String s = LoginController.getAcc().getID();
        taiLieuDaMuon = RequestDAO.getBorrowHistory(s);
    }
    
    
}
