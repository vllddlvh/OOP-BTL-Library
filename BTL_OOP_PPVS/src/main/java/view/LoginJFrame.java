package view;

import controller.LoginController;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Giao diện đăng nhập
 */
public class LoginJFrame extends javax.swing.JFrame {

    /**
     * Constructor khởi tạo giao diện đăng nhập
     */
    public LoginJFrame() {
        initComponents();
        this.setLocationByPlatform(rootPaneCheckingEnabled);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    }
    
    

    /**
     * Khởi tạo các thành phần giao diện
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlbLogoUet = new javax.swing.JLabel();
        jlbLogin = new javax.swing.JLabel();
        jlbUsername = new javax.swing.JLabel();
        jlbPassword = new javax.swing.JLabel();
        ButtonLogin = new javax.swing.JButton();
        jtfUsername = new javax.swing.JTextField();
        jpfPassword = new javax.swing.JPasswordField();
        jlbMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(290, 420));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jlbLogoUet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoUET Login.svg.png"))); // NOI18N
        jlbLogoUet.setText("jLabel1");

        jlbLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlbLogin.setText("Đăng Nhập");

        jlbUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbUsername.setText("Tài khoản:");

        jlbPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbPassword.setText("Mật khẩu:");

        ButtonLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ButtonLogin.setText("Đăng nhập");
        ButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLoginActionPerformed(evt);
            }
        });

        jtfUsername.setMaximumSize(new java.awt.Dimension(64, 22));

        jpfPassword.setMaximumSize(new java.awt.Dimension(64, 22));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbLogoUet, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbLogin))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 62, Short.MAX_VALUE)
                .addComponent(jlbMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlbUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlbPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jpfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jtfUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(ButtonLogin)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jlbLogoUet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlbLogin)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbUsername)
                    .addComponent(jtfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbPassword)
                    .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(ButtonLogin)
                .addGap(18, 18, 18)
                .addComponent(jlbMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Xử lý sự kiện khi nhấn nút Đăng nhập
     */
    private void ButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLoginActionPerformed
        String username = jtfUsername.getText().trim();
        String password = String.valueOf(jpfPassword.getPassword()).trim();

        try {
            if (username.isEmpty()) {
                jlbMessage.setText("Vui lòng nhập tên tài khoản");
            } else if (password.isEmpty()) {
                jlbMessage.setText("Vui lòng nhập mật khẩu");
            } else {
                if (!LoginController.login(username, password)) {
                    jlbMessage.setText("Tên đăng nhập và mật khẩu không đúng!");
                } else {
                    this.dispose();
                }
            }
        } catch (SQLException ex) {
            jlbMessage.setText("Lỗi hệ thống: " + ex.getMessage());
            Logger.getLogger(LoginJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ButtonLoginActionPerformed

    private void mouseClickedActionPerformed(MouseEvent e) {
        if (e.getClickCount() < 1) return;
        try {
            if (jtfUsername.getText().trim().length() == 0) {
                jlbMessage.setText("Vui lòng nhập tên tài khoản");
            } else if (jpfPassword.getText().trim().length() == 0) {
                jlbMessage.setText("Vui lòng nhập mật khẩu");
            } else {
                if (!LoginController.login(jtfUsername.getText(), jpfPassword.getText())) {
                    jlbMessage.setText("Tên đăng nhập và mật khẩu không đúng!");
                } else {
                    this.dispose();
                }
            }
        } catch (SQLException ex) {
            jlbMessage.setText(ex.toString());
            Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonLogin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlbLogin;
    private javax.swing.JLabel jlbLogoUet;
    private javax.swing.JLabel jlbMessage;
    private javax.swing.JLabel jlbPassword;
    private javax.swing.JLabel jlbUsername;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JTextField jtfUsername;
    // End of variables declaration//GEN-END:variables
}
