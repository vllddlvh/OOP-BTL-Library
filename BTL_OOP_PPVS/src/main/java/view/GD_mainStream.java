package view;

import Model.User;
import Controller.DatabaseConnector;
import java.sql.SQLException;

/**
 * @author Little
 * 
 * Original line. To make sure all JFrame using by the same user.
 * And maybe, operate what JFrame be set visible.
 */
public abstract class GD_mainStream extends javax.swing.JFrame {
    
    protected static User currentUser = new User();
    
    public GD_mainStream() {
        try {
            DatabaseConnector.firstTODO();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
    
//    public void openFrame() {
//        // TODO add your handling code here:
//        mainStream mainStream = new GDBanDoc();
//        
//        // Hiển thị cửa sổ BanDoc
//        this.setVisible(true);
//
//        // Ẩn cửa sổ hiện tại (tùy chọn)
//        this.setVisible(false);
//    }
    
}
