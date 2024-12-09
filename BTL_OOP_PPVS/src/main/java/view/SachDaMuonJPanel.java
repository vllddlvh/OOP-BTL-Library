/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import controller.UpdateTableMuonTraTaiLieu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class SachDaMuonJPanel extends javax.swing.JPanel {

    /**
     * Creates new form MuonTraTaiLieuJPanel
     */
    public SachDaMuonJPanel() {
        initComponents();
        initTableMuonTraSach();
        
        UpdateTableMuonTraTaiLieu.initTable(UpdateTableMuonTraTaiLieu.MY_HISTORY);
        UpdateTableMuonTraTaiLieu ctrl = UpdateTableMuonTraTaiLieu.getInstance();
        try {
            ctrl.setTableUpToDate(jTableMuonTraSach, null, JTextFieldTimKiemThongTinSachMuonTra);
            
        } catch (SQLException ex) {
            Logger.getLogger(SachDaMuonJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void initTableMuonTraSach() {
        jTableMuonTraSach.setEnabled(true);
        jTableMuonTraSach.getTableHeader().setFont(new Font("Arrial", Font.BOLD, 14));
        jTableMuonTraSach.getTableHeader().setForeground(Color.white);
        jTableMuonTraSach.getTableHeader().setPreferredSize(new Dimension(100,50));
        jTableMuonTraSach.getTableHeader().setBackground(new Color(80,141,78));
        jTableMuonTraSach.setRowHeight(40);
        jTableMuonTraSach.validate();
        jTableMuonTraSach.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        JTextFieldTimKiemThongTinSachMuonTra = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMuonTraSach = new javax.swing.JTable();
        isUnReturnCheckBox = new javax.swing.JCheckBox();

        jPanel1.setBackground(new java.awt.Color(128, 175, 129));

        JTextFieldTimKiemThongTinSachMuonTra.setForeground(new java.awt.Color(204, 204, 204));
        JTextFieldTimKiemThongTinSachMuonTra.setText("Tìm kiếm thông tin mượn trả");
        JTextFieldTimKiemThongTinSachMuonTra.setPreferredSize(new java.awt.Dimension(170, 25));
        JTextFieldTimKiemThongTinSachMuonTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                JTextFieldTimKiemThongTinSachMuonTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                JTextFieldTimKiemThongTinSachMuonTraFocusLost(evt);
            }
        });
        JTextFieldTimKiemThongTinSachMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextFieldTimKiemThongTinSachMuonTraActionPerformed(evt);
            }
        });

        jTableMuonTraSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã số yêu cầu", "Mã người mượn", "Tên người mượn", "Mã sách", "Tên sách", "Ngày mượn", "Ngày trả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableMuonTraSach.setToolTipText("");
        jTableMuonTraSach.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTableMuonTraSach);

        isUnReturnCheckBox.setBackground(new java.awt.Color(128, 175, 129));
        isUnReturnCheckBox.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        isUnReturnCheckBox.setText("Chỉ yêu cầu chưa trả tài liệu");
        isUnReturnCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isUnReturnCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(JTextFieldTimKiemThongTinSachMuonTra, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(isUnReturnCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 44, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTextFieldTimKiemThongTinSachMuonTra, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isUnReturnCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void JTextFieldTimKiemThongTinSachMuonTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JTextFieldTimKiemThongTinSachMuonTraFocusGained
        // TODO add your handling code here:
        JTextFieldTimKiemThongTinSachMuonTra.setText("");
        JTextFieldTimKiemThongTinSachMuonTra.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12)); // Đặt phông là Segoe UI, kích thước 14
        JTextFieldTimKiemThongTinSachMuonTra.setForeground(java.awt.Color.BLACK); // Đặt màu chữ là đen
    }//GEN-LAST:event_JTextFieldTimKiemThongTinSachMuonTraFocusGained

    private void JTextFieldTimKiemThongTinSachMuonTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JTextFieldTimKiemThongTinSachMuonTraFocusLost
        // TODO add your handling code here:
        if(JTextFieldTimKiemThongTinSachMuonTra.getText().equals("")) {
            JTextFieldTimKiemThongTinSachMuonTra.setText("Tìm kiếm thông tin Yêu cầu Mượn/Trả tài liệu");
            JTextFieldTimKiemThongTinSachMuonTra.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12)); // Đặt phông là Segoe UI, kích thước 14
            JTextFieldTimKiemThongTinSachMuonTra.setForeground(new Color(204,204,204));
        }
    }//GEN-LAST:event_JTextFieldTimKiemThongTinSachMuonTraFocusLost

    private void JTextFieldTimKiemThongTinSachMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextFieldTimKiemThongTinSachMuonTraActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_JTextFieldTimKiemThongTinSachMuonTraActionPerformed

    private void isUnReturnCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isUnReturnCheckBoxActionPerformed
        // TODO add your handling code here:
        
        if (isUnReturnCheckBox.isSelected()) {
            UpdateTableMuonTraTaiLieu.unreturnFilter();
        } else {
            UpdateTableMuonTraTaiLieu.rewriteTable();
        }
    }//GEN-LAST:event_isUnReturnCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldTimKiemThongTinSachMuonTra;
    private javax.swing.JCheckBox isUnReturnCheckBox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMuonTraSach;
    // End of variables declaration//GEN-END:variables
}
