/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.UpdateDocumentTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.dao.FileFormatException;
import model.entity.Book;

/**
 *
 * @// author HP
 */
public class SuaThongTinTaiLieuSachFrame extends javax.swing.JFrame {

    /**
     * Creates new form ThemTaiLieuFrame
     */
    public SuaThongTinTaiLieuSachFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public SuaThongTinTaiLieuSachFrame(Book book) {
    // Khởi tạo giao diện
        initComponents();

        // Gán giá trị vào các JTextField
        jTextAreaSummary.setText(book.getDescription());
        jTextFieldBookAuthor.setText(book.getAuthor());
        jTextFieldBookID.setText(book.getID());
        jTextFieldBookPublisher.setText(book.getPublisher());
        jTextFieldBookTitle.setText(book.getTitle());
        jTextFieldCategory.setText(book.getCategory().toString());
        jTextFieldLanguage.setText(book.getLanguage());
        jTextFieldPublicationYear.setText(String.valueOf(book.getReleaseYear()));
        jTextFieldFileImage.setText("Không biêt"); 
        // tạm tạm vậy. Tại ảnh bìa sẽ lưu trong cache (RAM), nên không có filePath đến đó.
        // Bù lại thì đỡ công xóa.
        // Nhưng cũng khó khi API toàn trả về URL.

        // Đặt trường ID không thể chỉnh sửa
        jTextFieldBookID.setEditable(false);

        // Cài đặt chế độ đóng cửa sổ
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

        jpnViewThemTaiLieu = new javax.swing.JPanel();
        jpnThemTaiLieu = new javax.swing.JPanel();
        jlbID = new javax.swing.JLabel();
        jlbTitle = new javax.swing.JLabel();
        jlbAuthor = new javax.swing.JLabel();
        jlbCategory = new javax.swing.JLabel();
        jlbPublicationYear = new javax.swing.JLabel();
        jlbPublisher = new javax.swing.JLabel();
        ButtonReset = new javax.swing.JButton();
        jTextFieldBookTitle = new javax.swing.JTextField();
        jTextFieldBookID = new javax.swing.JTextField();
        jTextFieldBookAuthor = new javax.swing.JTextField();
        jTextFieldBookPublisher = new javax.swing.JTextField();
        jTextFieldPublicationYear = new javax.swing.JTextField();
        jTextFieldCategory = new javax.swing.JTextField();
        ButtonXoaTaiLieu = new javax.swing.JButton();
        jlbLanguage = new javax.swing.JLabel();
        jlbSummary = new javax.swing.JLabel();
        jlbImage = new javax.swing.JLabel();
        jTextFieldLanguage = new javax.swing.JTextField();
        jTextFieldFileImage = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaSummary = new javax.swing.JTextArea();
        ButtonSaveBook = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpnViewThemTaiLieu.setBackground(new java.awt.Color(214, 239, 216));

        jpnThemTaiLieu.setBackground(new java.awt.Color(128, 175, 129));
        jpnThemTaiLieu.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin tài liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jpnThemTaiLieu.setForeground(new java.awt.Color(255, 255, 255));
        jpnThemTaiLieu.setName(""); // NOI18N

        jlbID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbID.setForeground(new java.awt.Color(255, 255, 255));
        jlbID.setText("ID         ");

        jlbTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbTitle.setForeground(new java.awt.Color(255, 255, 255));
        jlbTitle.setText("Tiêu đề");

        jlbAuthor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbAuthor.setForeground(new java.awt.Color(255, 255, 255));
        jlbAuthor.setText("Tác giả");

        jlbCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbCategory.setForeground(new java.awt.Color(255, 255, 255));
        jlbCategory.setText("Thể loại");

        jlbPublicationYear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbPublicationYear.setForeground(new java.awt.Color(255, 255, 255));
        jlbPublicationYear.setText("Năm xuất bản");

        jlbPublisher.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbPublisher.setForeground(new java.awt.Color(255, 255, 255));
        jlbPublisher.setText("Nhà xuất bản");

        ButtonReset.setBackground(new java.awt.Color(80, 141, 78));
        ButtonReset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButtonReset.setForeground(new java.awt.Color(255, 255, 255));
        ButtonReset.setText("Reset");
        ButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonResetActionPerformed(evt);
            }
        });

        jTextFieldBookPublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBookPublisherActionPerformed(evt);
            }
        });

        ButtonXoaTaiLieu.setBackground(new java.awt.Color(80, 141, 78));
        ButtonXoaTaiLieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButtonXoaTaiLieu.setForeground(new java.awt.Color(255, 255, 255));
        ButtonXoaTaiLieu.setText("Xóa tài liệu");
        ButtonXoaTaiLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaTaiLieuActionPerformed(evt);
            }
        });

        jlbLanguage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbLanguage.setForeground(new java.awt.Color(255, 255, 255));
        jlbLanguage.setText("Ngôn ngữ");

        jlbSummary.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbSummary.setForeground(new java.awt.Color(255, 255, 255));
        jlbSummary.setText("Tóm tắt");

        jlbImage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbImage.setForeground(new java.awt.Color(255, 255, 255));
        jlbImage.setText("Hình ảnh");

        jTextAreaSummary.setColumns(20);
        jTextAreaSummary.setRows(5);
        jScrollPane1.setViewportView(jTextAreaSummary);

        javax.swing.GroupLayout jpnThemTaiLieuLayout = new javax.swing.GroupLayout(jpnThemTaiLieu);
        jpnThemTaiLieu.setLayout(jpnThemTaiLieuLayout);
        jpnThemTaiLieuLayout.setHorizontalGroup(
            jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(ButtonXoaTaiLieu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbSummary, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jlbImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbAuthor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbPublisher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbPublicationYear, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                        .addComponent(jlbCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(78, 78, 78)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldBookPublisher)
                    .addComponent(jTextFieldPublicationYear)
                    .addComponent(jTextFieldCategory)
                    .addComponent(jTextFieldBookID)
                    .addComponent(jTextFieldBookTitle)
                    .addComponent(jTextFieldBookAuthor)
                    .addComponent(jTextFieldLanguage)
                    .addComponent(jTextFieldFileImage)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                .addGap(246, 246, 246))
        );
        jpnThemTaiLieuLayout.setVerticalGroup(
            jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBookID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBookAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBookPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbPublicationYear, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPublicationYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldFileImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonReset)
                    .addComponent(ButtonXoaTaiLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        ButtonSaveBook.setBackground(new java.awt.Color(80, 141, 78));
        ButtonSaveBook.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButtonSaveBook.setForeground(new java.awt.Color(255, 255, 255));
        ButtonSaveBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logoSaveAS.png"))); // NOI18N
        ButtonSaveBook.setText("Lưu dữ liệu");
        ButtonSaveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSaveBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnViewThemTaiLieuLayout = new javax.swing.GroupLayout(jpnViewThemTaiLieu);
        jpnViewThemTaiLieu.setLayout(jpnViewThemTaiLieuLayout);
        jpnViewThemTaiLieuLayout.setHorizontalGroup(
            jpnViewThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewThemTaiLieuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonSaveBook)
                .addContainerGap())
            .addGroup(jpnViewThemTaiLieuLayout.createSequentialGroup()
                .addComponent(jpnThemTaiLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpnViewThemTaiLieuLayout.setVerticalGroup(
            jpnViewThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewThemTaiLieuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonSaveBook, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpnThemTaiLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnThemTaiLieu.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewThemTaiLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewThemTaiLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonResetActionPerformed
        jTextAreaSummary.setText("");
        jTextFieldBookAuthor.setText("");
        jTextFieldBookID.setText("");
        jTextFieldBookPublisher.setText("");
        jTextFieldBookTitle.setText("");
        jTextFieldCategory.setText("");
        jTextFieldFileImage.setText("");
        jTextFieldLanguage.setText("");
        jTextFieldPublicationYear.setText("");
    }//GEN-LAST:event_ButtonResetActionPerformed

    private void jTextFieldBookPublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBookPublisherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBookPublisherActionPerformed

    private void ButtonXoaTaiLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaTaiLieuActionPerformed
        // TODO add your handling code here:
         // Tạo một Document với thông tin từ các JTextField
         
        Book doc = new Book(
                            jTextFieldBookID.getText(),
                           jTextFieldBookTitle.getText(),
                    1000,
                          jTextFieldBookAuthor.getText(),
                        jTextFieldBookPublisher.getText(),
                       Integer.parseInt(jTextFieldPublicationYear.getText()),
                
                       jTextAreaSummary.getText(),
                         jTextFieldCategory.getText(),
                         jTextFieldLanguage.getText()
                );

        try {
        UpdateDocumentTable ctrl = UpdateDocumentTable.getUpdateDocumentTable();
            if (ctrl.deleteElement(doc)) {
                JOptionPane.showMessageDialog(rootPane, "Xóa tài liệu thành công");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Xóa tài liệu không thành công");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SuaThongTinTaiLieuSachFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_ButtonXoaTaiLieuActionPerformed

    private void ButtonSaveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSaveBookActionPerformed
        // TODO add your handling code here:
        Book doc = new Book(
                            jTextFieldBookID.getText(),
                           jTextFieldBookTitle.getText(),
                    1000,
                          jTextFieldBookAuthor.getText(),
                        jTextFieldBookPublisher.getText(),
                       Integer.parseInt(jTextFieldPublicationYear.getText()),
                       jTextAreaSummary.getText(),
                         jTextFieldCategory.getText(),
                         jTextFieldLanguage.getText()
                );
   
        try {
            UpdateDocumentTable ctrl = UpdateDocumentTable.getUpdateDocumentTable();
            if (ctrl.updateElement(doc)) {
                JOptionPane.showMessageDialog(rootPane, "Sửa thành công");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sửa không thành công");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            
        } catch (IOException ex) {
            Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } catch (FileFormatException ex) {
            Logger.getLogger(SuaThongTinThanhVienJFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_ButtonSaveBookActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
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
            java.util.logging.Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThemTaiLieuFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonReset;
    private javax.swing.JButton ButtonSaveBook;
    private javax.swing.JButton ButtonXoaTaiLieu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaSummary;
    private javax.swing.JTextField jTextFieldBookAuthor;
    private javax.swing.JTextField jTextFieldBookID;
    private javax.swing.JTextField jTextFieldBookPublisher;
    private javax.swing.JTextField jTextFieldBookTitle;
    private javax.swing.JTextField jTextFieldCategory;
    private javax.swing.JTextField jTextFieldFileImage;
    private javax.swing.JTextField jTextFieldLanguage;
    private javax.swing.JTextField jTextFieldPublicationYear;
    private javax.swing.JLabel jlbAuthor;
    private javax.swing.JLabel jlbCategory;
    private javax.swing.JLabel jlbID;
    private javax.swing.JLabel jlbImage;
    private javax.swing.JLabel jlbLanguage;
    private javax.swing.JLabel jlbPublicationYear;
    private javax.swing.JLabel jlbPublisher;
    private javax.swing.JLabel jlbSummary;
    private javax.swing.JLabel jlbTitle;
    private javax.swing.JPanel jpnThemTaiLieu;
    private javax.swing.JPanel jpnViewThemTaiLieu;
    // End of variables declaration//GEN-END:variables
}
