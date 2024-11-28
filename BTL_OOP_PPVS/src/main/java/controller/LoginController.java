
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

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

    public static User getAcc() {
        return currentUser;
    }

    public static void setAcc(User acc) {
        LoginController.currentUser = acc;
        if (currentUser instanceof Staff) {
            new GDMain().setVisible(true);
        } else {
            new GDMainNguoiDung().setVisible(true);
        }
    }
    
}
