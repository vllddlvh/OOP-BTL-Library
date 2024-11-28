package view;

import controller.LoginController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.dao.FileFormatException;
import model.entity.Book;
import model.entity.Staff;

/**
 *
 * @author FuK
 */
public class ChiTietTaiLieu extends javax.swing.JFrame {
    
    private Book currentShowDocument;
    
    /**
     * Hiển thị thông tin chi tiết sách trong cửa sổ mới (JFrame)
     */
    public ChiTietTaiLieu(Book document) {
        currentShowDocument = document;
        
        // Setup frame mới (cửa sổ)
        this.setTitle("Chi Tiết Sách");
        {
            this.setSize(720, 500);
            this.setLayout(new BorderLayout());
        }
        
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        {
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mainPanel.setBackground(new Color(214, 239, 216));
        }

        // Panel hiển thị ảnh
        JPanel imagePanel = new JPanel();
        {
            imagePanel.setBackground(Color.WHITE);
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.TOP);

            // load ảnh bìa (cover)
            ImageIcon originalIcon = null;
            try {
                originalIcon = document.getCover();
                
            } catch (IOException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (FileFormatException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            
            if (originalIcon == null) {
                originalIcon = new ImageIcon("src\\main\\java\\image\\default-null-book-cover.png");
            }
    
            // Setup hiển thị ảnh bìa trên frame "Thông tin chi tiết"
            Image scaledImage = originalIcon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));

            imagePanel.add(imageLabel);
        }
        
        mainPanel.add(imagePanel, BorderLayout.WEST);
        

        // Panel hiển thị thông tin
        JPanel infoPanel = new JPanel();
        {
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(240, 240, 240));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextArea documentJTextArea = new JTextArea(
                "ID: " + document.getID() + "\n" +
                "Tên sách: " + document.getTitle() + "\n" +
                "Tác giả: " + document.getAuthor() + "\n" +
                "Ngôn ngữ: " + document.getLanguage() + "\n" +
                "Nhà suất bản: " + document.getPublisher() + "\n" +
                "Thể loại: " + document.getCategory() + "\n" +
                "Năm xuất bản: " + document.getReleaseYear() + "\n" +
                "Giới thiệu: " + document.getDescription()
            );
            documentJTextArea.setEditable(false);
            documentJTextArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            documentJTextArea.setLineWrap(true);
            documentJTextArea.setWrapStyleWord(true);
            documentJTextArea.setBackground(Color.WHITE);
            JScrollPane scrollPane = new JScrollPane(documentJTextArea);
            scrollPane.setBorder(null);

            infoPanel.add(scrollPane);
        }       

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        
        
        // Tạo nút mượn sách
        JPanel buttonPanel = new JPanel();
        {
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 220, 10)); // Buttons centered with spacing
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPanel.setBackground(Color.WHITE);

            // Create buttons
            tryButton = new JButton("Đọc thử");
            borrowButton = new JButton("Mượn");

            // Optional: Set preferred size for consistent button appearance
            tryButton.setPreferredSize(new Dimension(100, 40));
            borrowButton.setPreferredSize(new Dimension(100, 40));

            // Add action listeners to buttons (if needed)
            tryButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonTryActionPerformed(evt);
                }
            });
            borrowButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonBorrowActionPerformed(evt);
                }
            });

            // Add buttons to the panel
            buttonPanel.add(tryButton);
            buttonPanel.add(borrowButton);
        }

        
        // thêm nút vào panel chính
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        // Thêm panel chính vào JFrame
        this.add(mainPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }
    
    private void jButtonTryActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // try {
            // TODO add your handling code here:
            if (LoginController.getAcc().readDocument(currentShowDocument.getID())) {
                // Đáng nhẽ là open file PDF
                JOptionPane.showMessageDialog(this, "Chức năng Đọc thử đang phát triển!");
                
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi!!! Tạm thời không thể đọc tài liệu này");
            }
            
//        } catch (SQLException ex) {
//            Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(this, ex.getMessage());
//        }
    }          
    
    private void jButtonBorrowActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            if (LoginController.getAcc().borrowDocument(currentShowDocument.getID())) {
                // Đáng nhẽ là open file PDF
                JOptionPane.showMessageDialog(this, "Mượn thành công");
                
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi!!! Tạm thời không thể mượn tài liệu này");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }   
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ChiTietTaiLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietTaiLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietTaiLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietTaiLieu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ChiTietTaiLieu(new Book("006")).setVisible(true);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JButton tryButton;
    private JButton borrowButton;

}
