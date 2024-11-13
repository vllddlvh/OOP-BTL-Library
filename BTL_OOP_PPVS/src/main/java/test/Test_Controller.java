package test;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.*;
import controller.*;
import java.util.Scanner;

/**
 *
 * @author Littl
 */
public class Test_Controller {
    private static boolean addMember() {
        Member newOne = new Member("1686", 
                              "Ngx", 
                               "Sonw");
        
        try {
            return DBUser.addNewMember(newOne);
            
        } catch (SQLException ex) {
            Logger.getLogger(Test_Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    private static boolean addStaff() {
        Staff newOne = new Staff("Tester", 
                            "Ngx", 
                             "Sonw");
        
        try {
            return DBUser.addNewStaff(newOne, 
                                             new Staff("U001", "", ""));
            
        } catch (SQLException ex) {
            Logger.getLogger(Test_Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    private static User.loginAlert login(String password) {
        try {
            return DBLogin.login("Tester", password);
            
        } catch (SQLException ex) {
            Logger.getLogger(Test_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static boolean changePassword(String oldPass, String newPass) {
        try {
            return DBLogin.changePassword("Tester", oldPass, newPass);
            
        } catch (SQLException ex) {
            Logger.getLogger(Test_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void main(String[] args) {
        try {
            DatabaseConnector.firstTODO();
        } catch (SQLException ex) {
            Logger.getLogger(Test_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Scanner sc = new Scanner(System.in);
        
        addStaff();
        
        User.loginAlert temp = null;
        while (temp != User.loginAlert.CORRECT_PASSWORD) {
            
            System.out.println("User: Tester\nPassword: ");
            temp = login(sc.next());
            switch (temp) {
                case FIRST_TIMES_LOGIN:
                    System.out.println("FIRST_TIMES_LOGIN");
                    System.out.println("Set password for the first time.\nNew password: ");
                    changePassword("Tester", sc.next());
                    System.out.println("\n\n\n\n\n\n\n\n\n");
                    break;
                    
                case WRONG_PASSWORD:
                    System.out.println("\n\n\n\n\n\n\n\n\n");
                    System.out.println("WRONG_PASSWORD");
                    break;
                    
                case CORRECT_PASSWORD:
                    System.out.println("CORRECT_PASSWORD");
                    break;
                    
                default:
                    System.out.println("UNKNOWN ERROR");
                    break;
            }
        }
    }
}
