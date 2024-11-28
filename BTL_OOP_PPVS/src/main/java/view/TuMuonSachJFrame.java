/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.JFrame;
import model.entity.Document;
import model.entity.User;

/**
 *
 * @author ADMIN
 */
public class TuMuonSachJFrame extends javax.swing.JFrame {

    /**
     * Creates new form TuMuonSachJFrame
     */
    public TuMuonSachJFrame(Document document, User currentUser) {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        jTextFieldUserID.setText(currentUser.getLastName() + currentUser.getFirstName());
        jTextFieldUserID.setEditable(false);
        JTextFieldDocumentID.setText(document.getID());
        JTextFieldDocumentID.setEditable(false);
    }

    /** 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBigMuonSach = new javax.swing.JPanel();
        jpnMuonSach = new javax.swing.JPanel();
        jlbDocumentID = new javax.swing.JLabel();
        jlbUserID = new javax.swing.JLabel();
        jlbquantityBorrow = new javax.swing.JLabel();
        JTextFieldDocumentID = new javax.swing.JTextField();
        jTextFieldUserID = new javax.swing.JTextField();
        jTextQuantityBorrow = new javax.swing.JTextField();
        jButtonLuuThongTin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelBigMuonSach.setBackground(new java.awt.Color(214, 239, 216));

        jpnMuonSach.setBackground(new java.awt.Color(128, 175, 129));
        jpnMuonSach.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mượn sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(255, 255, 255))); // NOI18N

        jlbDocumentID.setBackground(new java.awt.Color(255, 255, 255));
        jlbDocumentID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbDocumentID.setForeground(new java.awt.Color(255, 255, 255));
        jlbDocumentID.setText("documentID:");

        jlbUserID.setBackground(new java.awt.Color(255, 255, 255));
        jlbUserID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbUserID.setForeground(new java.awt.Color(255, 255, 255));
        jlbUserID.setText("userID:");

        jlbquantityBorrow.setBackground(new java.awt.Color(255, 255, 255));
        jlbquantityBorrow.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlbquantityBorrow.setForeground(new java.awt.Color(255, 255, 255));
        jlbquantityBorrow.setText("quantityBorrow:");

        JTextFieldDocumentID.setMaximumSize(new java.awt.Dimension(65, 22));
        JTextFieldDocumentID.setMinimumSize(new java.awt.Dimension(65, 22));

        jTextFieldUserID.setMaximumSize(new java.awt.Dimension(65, 22));
        jTextFieldUserID.setMinimumSize(new java.awt.Dimension(65, 22));

        jTextQuantityBorrow.setText("1");
        jTextQuantityBorrow.setMaximumSize(new java.awt.Dimension(65, 22));
        jTextQuantityBorrow.setMinimumSize(new java.awt.Dimension(65, 22));

        javax.swing.GroupLayout jpnMuonSachLayout = new javax.swing.GroupLayout(jpnMuonSach);
        jpnMuonSach.setLayout(jpnMuonSachLayout);
        jpnMuonSachLayout.setHorizontalGroup(
            jpnMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMuonSachLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpnMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpnMuonSachLayout.createSequentialGroup()
                        .addComponent(jlbDocumentID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JTextFieldDocumentID, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnMuonSachLayout.createSequentialGroup()
                        .addComponent(jlbUserID)
                        .addGap(58, 58, 58)
                        .addComponent(jTextFieldUserID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpnMuonSachLayout.createSequentialGroup()
                        .addComponent(jlbquantityBorrow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextQuantityBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(297, Short.MAX_VALUE))
        );
        jpnMuonSachLayout.setVerticalGroup(
            jpnMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMuonSachLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpnMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbDocumentID)
                    .addComponent(JTextFieldDocumentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jpnMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbUserID)
                    .addComponent(jTextFieldUserID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jpnMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbquantityBorrow)
                    .addComponent(jTextQuantityBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jButtonLuuThongTin.setBackground(new java.awt.Color(80, 141, 78));
        jButtonLuuThongTin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonLuuThongTin.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLuuThongTin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logoSaveAS.png"))); // NOI18N
        jButtonLuuThongTin.setText("Lưu thông tin");

        javax.swing.GroupLayout jPanelBigMuonSachLayout = new javax.swing.GroupLayout(jPanelBigMuonSach);
        jPanelBigMuonSach.setLayout(jPanelBigMuonSachLayout);
        jPanelBigMuonSachLayout.setHorizontalGroup(
            jPanelBigMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBigMuonSachLayout.createSequentialGroup()
                .addGroup(jPanelBigMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelBigMuonSachLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonLuuThongTin))
                    .addGroup(jPanelBigMuonSachLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jpnMuonSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        jPanelBigMuonSachLayout.setVerticalGroup(
            jPanelBigMuonSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBigMuonSachLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLuuThongTin)
                .addGap(15, 15, 15)
                .addComponent(jpnMuonSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelBigMuonSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBigMuonSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldDocumentID;
    private javax.swing.JButton jButtonLuuThongTin;
    private javax.swing.JPanel jPanelBigMuonSach;
    private javax.swing.JTextField jTextFieldUserID;
    private javax.swing.JTextField jTextQuantityBorrow;
    private javax.swing.JLabel jlbDocumentID;
    private javax.swing.JLabel jlbUserID;
    private javax.swing.JLabel jlbquantityBorrow;
    private javax.swing.JPanel jpnMuonSach;
    // End of variables declaration//GEN-END:variables
}
