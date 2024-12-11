package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.TreeSet;
import model.entity.Book;
import model.entity.Member;
import model.entity.Staff;
import model.entity.User;
import view.GDMain;
import view.GDMainNguoiDung;

/**
 *
 * @author HP
 */
public class LoginController {
    
    private static User currentUser;
    private static TreeSet<String> myUnreturnList = new TreeSet<>();
    
    public static boolean login(String acc, String password) throws SQLException {
        User.LoginAlert result = User.login(acc, password);
        switch (result) {
            case WRONG_PASSWORD -> {
                return false;
            }
            case CORRECT_PASSWORD_AS_STAFF -> setAcc(new Staff(acc));
            case CORRECT_PASSWORD_AS_MEMBER -> setAcc(new Member(acc));
        }
        
        return true;
    }

    public static User getAcc() {
        return currentUser;
    }

    public static void setAcc(User acc) throws SQLException {
        currentUser = acc;
        
        if (currentUser instanceof Staff) {
            new GDMain().setVisible(true);
        } else {
            new GDMainNguoiDung().setVisible(true);
        }
        
        List<model.entity.Request> unreturn = currentUser.getMyUnreturnDocument();
        for (model.entity.Request x : unreturn) {
            myUnreturnList.add(x.getDocumentID());
        }
        
    }
    
    public static boolean borrowDocument(Book document) throws SQLException {
        if (currentUser.borrowDocument(document.getID())) {
            myUnreturnList.add(document.getID());
            
            return true;
        }
        return false;
    }
    
    public static boolean returnDocument(String documentID) throws SQLException {
        if (currentUser.returnDocument(documentID)) {
            myUnreturnList.remove(documentID);
            
            return true;
        }
        return false;
    }
    
    public static boolean isBorrowingThis(Book document) {
        return myUnreturnList.contains(document.getID());
    }
    
}
