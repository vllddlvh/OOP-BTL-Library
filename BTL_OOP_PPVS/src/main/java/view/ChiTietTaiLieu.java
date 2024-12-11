package view;

import controller.LoginController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
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

/**
 *
 * @author FuK
 */
public class ChiTietTaiLieu extends javax.swing.JFrame {
    
    private Book currentShowDocument;
    private static Image defaultCoverImage;
    private boolean isBorrowing;
    
    
    /**
     * Hiển thị thông tin chi tiết sách trong cửa sổ mới (JFrame)
     * 
     * @param document
     */
    public ChiTietTaiLieu(Book document) {
        currentShowDocument = document;
        
        // kiểm tra xem người dùng hiên tại có mượn quyển này không
        isBorrowing = LoginController.isBorrowingThis(document); 
        
        initComponents();
        initDocumentCard();
    }   
    
    private void initDocumentCard() {
        if (defaultCoverImage == null) {
            try {
                defaultCoverImage = ImageIO.read(new File("src\\main\\java\\image\\default-null-book-cover.png"));
            } catch (IOException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Setup frame mới (cửa sổ)
        this.setTitle("Chi Tiết Sách");
        {
            this.setSize(720, 516);
            this.setLayout(new BorderLayout());
        }
        
        // Panel chính
        mainPanel = new JPanel(new BorderLayout());
        {
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mainPanel.setBackground(new Color(214, 239, 216));
        }

        // Panel phía trái WEST
        imagePanel = new JPanel(new BorderLayout()); // Sử dụng BorderLayout
        imagePanel.setBackground(Color.WHITE);
        
        // Label hiển thị ảnh
        imageLabel = new JLabel();
        {
            
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.TOP);

            // load ảnh bìa (cover)
            Image coverImage = null;
            try {
                coverImage = currentShowDocument.getCover();
                
            } catch (IOException | SQLException | FileFormatException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            if (coverImage == null) {
                coverImage = defaultCoverImage;
            }
            
            // Setup hiển thị ảnh bìa trên frame "Thông tin chi tiết"
            Image scaledImage = coverImage.getScaledInstance(300, 375, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        }
        
        // Tạo nút "Mượn trả sách"
        jbuttonMuonTra = new JButton();
        setTextButtonMuonTra();
        {  
            jbuttonMuonTra.setForeground(Color.WHITE);
            jbuttonMuonTra.setAlignmentX(Component.CENTER_ALIGNMENT);
            jbuttonMuonTra.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            jbuttonMuonTra.setPreferredSize(new Dimension(150, 40));
            jbuttonMuonTra.setFocusPainted(false);
            // Gán sự kiện cho nút
            jbuttonMuonTra.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jButtonMuonTraClicked(evt);
                }
            });
        }
        
        jbuttonDoc = new JButton();
        setTextButtonDoc();
        {
            jbuttonDoc.setForeground(Color.WHITE);
            jbuttonDoc.setAlignmentX(Component.CENTER_ALIGNMENT);
            jbuttonDoc.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            jbuttonDoc.setPreferredSize(new Dimension(150, 40)); 
            jbuttonDoc.setFocusPainted(false);
            // Gán sự kiện cho nút
            jbuttonDoc.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jButtonDocClicked(evt);
                }
            });
            
        }

        
        // Thêm ảnh và nút vào imagePanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout để bố trí dọc
        buttonPanel.add(Box.createVerticalStrut(2)); // Khoảng cách giữa 2 nút
        buttonPanel.add(Box.createVerticalGlue()); // Khoảng cách phía trên
        buttonPanel.add(jbuttonMuonTra);
        buttonPanel.add(jbuttonDoc);
        buttonPanel.add(Box.createVerticalGlue()); // Khoảng cách phía dưới
        
        imagePanel.add(imageLabel, BorderLayout.NORTH); // Ảnh nằm ở phía trên
        imagePanel.add(buttonPanel, BorderLayout.CENTER); // 2 button nằm dưới
        

        // Panel hiển thị thông tin
        this.infoPanel = new JPanel();
        {
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(240, 240, 240));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextArea documentJTextArea = new JTextArea(
                "ID: " + currentShowDocument.getID() + "\n" +
                "Tên sách: " + currentShowDocument.getTitle() + "\n" +
                "Tác giả: " + currentShowDocument.getAuthor() + "\n" +
                "Ngôn ngữ: " + currentShowDocument.getLanguage() + "\n" +
                "Nhà suất bản: " + currentShowDocument.getPublisher() + "\n" +
                "Thể loại: " + currentShowDocument.getCategory() + "\n" +
                "Năm xuất bản: " + currentShowDocument.getReleaseYear() + "\n" +
                "Giới thiệu: " + currentShowDocument.getDescription()
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

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(infoPanel, BorderLayout.CENTER);


        // Thêm panel chính vào JFrame
        this.add(mainPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
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

    private void setTextButtonMuonTra() {
        if (isBorrowing) {
            jbuttonMuonTra.setText("Trả sách");
            jbuttonMuonTra.setBackground(Color.RED);
        } else {
            jbuttonMuonTra.setText("Mượn sách");
            jbuttonMuonTra.setBackground(new Color(80, 141, 78));
        }
    }
    
    private void setTextButtonDoc() {
        jbuttonDoc.setText("Đọc thử");
        jbuttonDoc.setBackground(new Color(0, 162, 232));
    }
    
    private void jButtonMuonTraClicked(java.awt.event.MouseEvent evt) {                                             
        // TODO add your handling code here:
        int n = evt.getClickCount();
        if (n >= 1) {
            try {
                if (isBorrowing) {
                    // Thực hiện trả đối với tài liệu đã mượn
                    if (LoginController.returnDocument(currentShowDocument.getID())) {
                        JOptionPane.showMessageDialog(this, "Trả sách thành công");
                        isBorrowing = !isBorrowing;
                        setTextButtonMuonTra();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi!!! Tạm thời không thể trả tài liệu này");
                    }
                    
                } else {
                    // Thực hiện mượn đối với tài liệu mới trắng
                    if (LoginController.borrowDocument(currentShowDocument)) {
                        JOptionPane.showMessageDialog(this, "Mượn sách thành công");
                        isBorrowing = !isBorrowing;
                        setTextButtonMuonTra();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi!!! Tạm thời không thể mượn tài liệu này");
                    }
                }
            
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }   
    
    private void jButtonDocClicked(java.awt.event.MouseEvent evt) { 
        try {
            if (currentShowDocument.getPDF() == null) {
                JOptionPane.showMessageDialog(this, 
                                                "Hiện tại sách không có bản đọc thử.", 
                                                "Thông báo", 
                                            JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            currentShowDocument.openPDF();
            
        } catch (IOException | SQLException | URISyntaxException ex) {
            Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, 
                                                "Đã xảy ra lỗi:\n" + ex.getMessage(), 
                                                "Lỗi", 
                                            JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JButton jbuttonMuonTra;
    private JButton jbuttonDoc;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JPanel infoPanel;
    private JPanel mainPanel;
    
}
