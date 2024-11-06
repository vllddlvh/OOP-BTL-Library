/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

/**
 *
 * @author ADMIN
 */
public class GDMenu extends javax.swing.JFrame {

    /**
     * Creates new form GDMenu
     */
    public GDMenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelThuVien = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButtonTongQuan = new javax.swing.JButton();
        jButtonSach = new javax.swing.JButton();
        jButtonBanDoc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(720, 450));

        jLabelThuVien.setBackground(new java.awt.Color(204, 204, 204));
        jLabelThuVien.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabelThuVien.setText("THƯ VIỆN");
        jLabelThuVien.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.Y_AXIS));

        jButtonTongQuan.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButtonTongQuan.setText("Tổng quan");
        jButtonTongQuan.setMaximumSize(new java.awt.Dimension(150, 30));
        jButtonTongQuan.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel16.add(jButtonTongQuan);

        jButtonSach.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButtonSach.setText("Sách");
        jButtonSach.setMaximumSize(new java.awt.Dimension(150, 30));
        jButtonSach.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSachActionPerformed(evt);
            }
        });
        jPanel16.add(jButtonSach);

        jButtonBanDoc.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButtonBanDoc.setText("Bạn đọc");
        jButtonBanDoc.setMaximumSize(new java.awt.Dimension(150, 30));
        jButtonBanDoc.setPreferredSize(new java.awt.Dimension(150, 30));
        jButtonBanDoc.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonBanDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBanDocActionPerformed(evt);
            }
        });
        jPanel16.add(jButtonBanDoc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(jLabelThuVien)
                .addContainerGap(323, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabelThuVien, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSachActionPerformed
        // TODO add your handling code here:
        GDSach book = new GDSach();
        book.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonSachActionPerformed

    private void jButtonBanDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBanDocActionPerformed
        // TODO add your handling code here:
        GDBanDoc banDoc = new GDBanDoc();
        // Hiển thị cửa sổ BanDoc
    banDoc.setVisible(true);

    // Ẩn cửa sổ hiện tại (tùy chọn)
    this.setVisible(false);
    }//GEN-LAST:event_jButtonBanDocActionPerformed

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
            java.util.logging.Logger.getLogger(GDMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GDMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GDMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GDMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GDMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBanDoc;
    private javax.swing.JButton jButtonSach;
    private javax.swing.JButton jButtonTongQuan;
    private javax.swing.JLabel jLabelThuVien;
    private javax.swing.JPanel jPanel16;
    // End of variables declaration//GEN-END:variables
}
