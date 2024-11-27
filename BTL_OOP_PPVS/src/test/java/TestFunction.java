
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Staff;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Littl
 */
public class TestFunction {
    public static void main(String[] args) {
        try {
            Staff Phuc = new Staff("PhucTester");
            
            System.out.println(Phuc.getID());
            System.out.println(Phuc.getFirstName());
            System.out.println(Phuc.getJobTitle());
            
        } catch (SQLException ex) {
            Logger.getLogger(TestFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
