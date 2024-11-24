package view;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.entity.Member;
import controller.UpdateMemberTable;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class SuaThongTinThanhVienJFrame extends javax.swing.JFrame {
    /**
     * Creates new form ThemThanhVienJFrame
     */
    public SuaThongTinThanhVienJFrame() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public SuaThongTinThanhVienJFrame(String id, String firstName, String lastName, String contact, String dateOfBirth) {
        initComponents();
        JTextFieldID.setText(id);
        jTextFieldFirstName.setText(firstName);
        jTextFieldLastName.setText(lastName);
        jTextFieldContact.setText(contact);
        jTextFieldDateOfBirth.setText(dateOfBirth);
        JTextFieldID.setEditable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnViewThemThanhVien = new javax.swing.JPanel();
        jpnThongTinThanhVien = new javax.swing.JPanel();
        jlbID = new javax.swing.JLabel();
        jlbFirstName = new javax.swing.JLabel();
        jlbLastName = new javax.swing.JLabel();
        jlbContact = new javax.swing.JLabel();
        jlbDateOfBirth = new javax.swing.JLabel();
        JTextFieldID = new javax.swing.JTextField();
        jTextFieldFirstName = new javax.swing.JTextField();
        jTextFieldLastName = new javax.swing.JTextField();
        jTextFieldContact = new javax.swing.JTextField();
        jTextFieldDateOfBirth = new javax.swing.JTextField();
        jButtonReset = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jButtonLuuDuLieu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnViewThemThanhVien.setBackground(new java.awt.Color(214, 239, 216));

        jpnThongTinThanhVien.setBackground(new java.awt.Color(128, 175, 129));
        jpnThongTinThanhVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin thành viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(255, 255, 255))); // NOI18N

        jlbID.setBackground(new java.awt.Color(255, 255, 255));
        jlbID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbID.setForeground(new java.awt.Color(255, 255, 255));
        jlbID.setText("ID:");

        jlbFirstName.setBackground(new java.awt.Color(255, 255, 255));
        jlbFirstName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbFirstName.setForeground(new java.awt.Color(255, 255, 255));
        jlbFirstName.setText("FirstName:");

        jlbLastName.setBackground(new java.awt.Color(255, 255, 255));
        jlbLastName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbLastName.setForeground(new java.awt.Color(255, 255, 255));
        jlbLastName.setText("LastName:");

        jlbContact.setBackground(new java.awt.Color(255, 255, 255));
        jlbContact.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbContact.setForeground(new java.awt.Color(255, 255, 255));
        jlbContact.setText("Contact:");

        jlbDateOfBirth.setBackground(new java.awt.Color(255, 255, 255));
        jlbDateOfBirth.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbDateOfBirth.setForeground(new java.awt.Color(255, 255, 255));
        jlbDateOfBirth.setText("DateOfBirth:");

        jButtonReset.setBackground(new java.awt.Color(80, 141, 78));
        jButtonReset.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonReset.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jButtonXoa.setBackground(new java.awt.Color(80, 141, 78));
        jButtonXoa.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonXoa.setForeground(new java.awt.Color(255, 255, 255));
        jButtonXoa.setText("Xóa thành viên");
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnThongTinThanhVienLayout = new javax.swing.GroupLayout(jpnThongTinThanhVien);
        jpnThongTinThanhVien.setLayout(jpnThongTinThanhVienLayout);
        jpnThongTinThanhVienLayout.setHorizontalGroup(
            jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongTinThanhVienLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpnThongTinThanhVienLayout.createSequentialGroup()
                        .addComponent(jButtonReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonXoa))
                    .addGroup(jpnThongTinThanhVienLayout.createSequentialGroup()
                        .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbDateOfBirth))
                        .addGap(15, 15, 15)
                        .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldContact, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jlbContact)
                    .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jlbLastName)
                        .addComponent(jlbFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(321, Short.MAX_VALUE))
        );
        jpnThongTinThanhVienLayout.setVerticalGroup(
            jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongTinThanhVienLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbID)
                    .addComponent(JTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbFirstName)
                    .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbLastName)
                    .addComponent(jTextFieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbContact)
                    .addComponent(jTextFieldContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbDateOfBirth)
                    .addComponent(jTextFieldDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReset)
                    .addComponent(jButtonXoa))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jButtonLuuDuLieu.setBackground(new java.awt.Color(80, 141, 78));
        jButtonLuuDuLieu.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonLuuDuLieu.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLuuDuLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logoSaveAS.png"))); // NOI18N
        jButtonLuuDuLieu.setText("Lưu dữ liệu");
        jButtonLuuDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLuuDuLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnViewThemThanhVienLayout = new javax.swing.GroupLayout(jpnViewThemThanhVien);
        jpnViewThemThanhVien.setLayout(jpnViewThemThanhVienLayout);
        jpnViewThemThanhVienLayout.setHorizontalGroup(
            jpnViewThemThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnViewThemThanhVienLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jpnViewThemThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnThongTinThanhVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewThemThanhVienLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonLuuDuLieu)))
                .addGap(5, 5, 5))
        );
        jpnViewThemThanhVienLayout.setVerticalGroup(
            jpnViewThemThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewThemThanhVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLuuDuLieu)
                .addGap(15, 15, 15)
                .addComponent(jpnThongTinThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewThemThanhVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewThemThanhVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        // TODO add your handling code here:
        JTextFieldID.setText("");
        jTextFieldContact.setText("");
        jTextFieldFirstName.setText("");
        jTextFieldLastName.setText("");
        jTextFieldDateOfBirth.setText("");
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonLuuDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLuuDuLieuActionPerformed
        // TODO add your handling code here:
        Member s = new Member(JTextFieldID.getText(),
                         jTextFieldFirstName.getText(),
                          jTextFieldLastName.getText(),
                           jTextFieldContact.getText(),
                        jTextFieldDateOfBirth.getText());
   
        try {
            UpdateMemberTable ctrl = UpdateMemberTable.getUpdateMemberTable();
            if (ctrl.updateElement(s)) {
                JOptionPane.showMessageDialog(rootPane, "Sửa thành công");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sửa không thành công");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Oops!!! Unknown Error");
        }
        
    }//GEN-LAST:event_jButtonLuuDuLieuActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        // TODO add your handling code here:
        Member s = new Member(JTextFieldID.getText(),
                         jTextFieldFirstName.getText(),
                          jTextFieldLastName.getText(),
                           jTextFieldContact.getText(),
                        jTextFieldDateOfBirth.getText());
   
        try {
            UpdateMemberTable ctrl = UpdateMemberTable.getUpdateMemberTable();
            if (ctrl.deleteElement(s)) {
                JTextFieldID.setText("");
                jTextFieldFirstName.setText("");
                jTextFieldLastName.setText("");
                jTextFieldContact.setText("");
                jTextFieldDateOfBirth.setText("");
                JOptionPane.showMessageDialog(rootPane, "Xóa thành công");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Xóa không thành công");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Oops!!! Unknown Error");
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SuaThongTinThanhVienJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldID;
    private javax.swing.JButton jButtonLuuDuLieu;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JTextField jTextFieldContact;
    private javax.swing.JTextField jTextFieldDateOfBirth;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JLabel jlbContact;
    private javax.swing.JLabel jlbDateOfBirth;
    private javax.swing.JLabel jlbFirstName;
    private javax.swing.JLabel jlbID;
    private javax.swing.JLabel jlbLastName;
    private javax.swing.JPanel jpnThongTinThanhVien;
    private javax.swing.JPanel jpnViewThemThanhVien;
    // End of variables declaration//GEN-END:variables
}
