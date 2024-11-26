/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.dao.AccountDAO;
import model.entity.Account;
import view.GDMain;

/**
 *
 * @author HP
 */
public class LoginController {
    private JFrame FrameLogin;
    private JButton ButtonSubmit;
    private JTextField jtfUsername;
    private JPasswordField jpfPassword;
    private JLabel jlbMsg;
    
    
    public LoginController(JFrame FrameLogin, JButton ButtonSubmit, JTextField jtfUsername, JPasswordField jpfPassword, JLabel jlbMsg) {
        this.FrameLogin = FrameLogin;
        this.ButtonSubmit = ButtonSubmit;
        this.jtfUsername = jtfUsername;
        this.jpfPassword = jpfPassword;
        this.jlbMsg = jlbMsg;
    }
    
    public void setEvent() {
        ButtonSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (jtfUsername.getText().length() == 0
                            || jpfPassword.getText().length() == 0) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc!");
                    } else {
                        Account account = AccountDAO.Login(jtfUsername.getText(), new String(jpfPassword.getPassword()));
                        if (account == null) {
                            jlbMsg.setText("Tên đăng nhập và mật khẩu không đúng!");
                        } else {
                            FrameLogin.dispose();
                            if (account.getRole().equals("Staff")) {
                                new GDMain().setVisible(true);
                            } else {
                                // new giao dien user
                            }
                        }
                    }
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ButtonSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ButtonSubmit.setBackground(new Color(100, 221, 23));
            }
        });

    }
    
}
