package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.dao.RequestDAO;
import model.entity.Request;


public class UpdateTableTaiLieuDaMuon {
    private static ArrayList<Request> taiLieuDaMuon = new ArrayList<>();
    
    public UpdateTableTaiLieuDaMuon() throws SQLException {
        String s = LoginController.getAcc().getID();
        taiLieuDaMuon = RequestDAO.getBorrowHistory(s);
    }
    
    
}
