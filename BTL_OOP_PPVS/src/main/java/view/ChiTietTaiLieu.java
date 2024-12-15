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
 * Class representing the detailed information window for a specific book.
 */
public class ChiTietTaiLieu extends javax.swing.JFrame {
    
    private Book currentShowDocument;
    private static Image defaultCoverImage;
    private boolean isBorrowing;

    /**
     * Displays detailed book information in a new window.
     *
     * @param document The book object to display details for.
     * @param isFromApi Indicates if the book data comes from an external API.
     */
    public ChiTietTaiLieu(Book document, boolean isFromApi) {
        currentShowDocument = document;
        isBorrowing = LoginController.isBorrowingThis(document);

        initComponents();
        initDocumentCard();

        if (isFromApi && LoginController.getAcc() instanceof model.entity.Staff) {
            initThemVaoThuVienButton();
        }
    }   
    
    /**
     * Initializes the UI components to display book details.
     */
    private void initDocumentCard() {
        if (defaultCoverImage == null) {
            try {
                defaultCoverImage = ImageIO.read(new File("src\\main\\java\\image\\default-null-book-cover.png"));
            } catch (IOException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.setTitle("Chi Tiết Sách");
        this.setSize(720, 568);
        this.setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(214, 239, 216));

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.TOP);

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
        Image scaledImage = coverImage.getScaledInstance(300, 375, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));

        jbuttonMuonTra = new JButton();
        setTextButtonMuonTra();
        jbuttonMuonTra.setForeground(Color.WHITE);
        jbuttonMuonTra.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbuttonMuonTra.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        jbuttonMuonTra.setPreferredSize(new Dimension(150, 40));
        jbuttonMuonTra.setFocusPainted(false);
        jbuttonMuonTra.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonMuonTraClicked(evt);
            }
        });

        jbuttonDoc = new JButton();
        setTextButtonDoc();
        jbuttonDoc.setForeground(Color.WHITE);
        jbuttonDoc.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbuttonDoc.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        jbuttonDoc.setPreferredSize(new Dimension(150, 40));
        jbuttonDoc.setFocusPainted(false);
        jbuttonDoc.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDocClicked(evt);
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(jbuttonMuonTra);
        buttonPanel.add(Box.createVerticalStrut(7));
        buttonPanel.add(jbuttonDoc);

        imagePanel.add(imageLabel, BorderLayout.NORTH);
        imagePanel.add(buttonPanel, BorderLayout.CENTER);

        infoPanel = new JPanel();
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

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    /**
     * Initializes the "Add to Library" button for staff accounts.
     */
    private void initThemVaoThuVienButton() {
        JButton jbuttonThemVaoThuVien = new JButton("Thêm vào thư viện");
        jbuttonThemVaoThuVien.setForeground(Color.RED);
        jbuttonThemVaoThuVien.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbuttonThemVaoThuVien.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        jbuttonThemVaoThuVien.setPreferredSize(new Dimension(150, 40));
        jbuttonThemVaoThuVien.setFocusPainted(false);
        jbuttonThemVaoThuVien.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonThemVaoThuVienClicked(evt);
            }
        });
        buttonPanel.add(Box.createVerticalStrut(7));
        buttonPanel.add(jbuttonThemVaoThuVien);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        if (isBorrowing) {
            jbuttonDoc.setText("Mở tài liệu");
            jbuttonDoc.setBackground(new Color(0, 162, 232));
        } else {
            jbuttonDoc.setText("Đọc thử");
            jbuttonDoc.setBackground(new Color(0, 162, 232));
        }
    }
    
    private void jButtonMuonTraClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() >= 1) {
            try {
                if (isBorrowing) {
                    if (LoginController.returnDocument(currentShowDocument.getID())) {
                        JOptionPane.showMessageDialog(this, "Trả sách thành công");
                        isBorrowing = !isBorrowing;
                        setTextButtonMuonTra();
                        setTextButtonDoc();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi!!! Tạm thời không thể trả tài liệu này");
                    }
                } else {
                    if (LoginController.borrowDocument(currentShowDocument)) {
                        JOptionPane.showMessageDialog(this, "Mượn sách thành công");
                        isBorrowing = !isBorrowing;
                        setTextButtonMuonTra();
                        setTextButtonDoc();
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
        if (evt.getClickCount() > 0) {
            try {
                if (isBorrowing) {
                    if (currentShowDocument.openFullPDF()) {
                        return;
                    }
                } else {
                    if (currentShowDocument.openSamplePDF()) {
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this,
                        "Hiện tại sách không có bản đọc thử.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | SQLException | URISyntaxException ex) {
                Logger.getLogger(ChiTietTaiLieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,
                        "Đã xảy ra lỗi:\n" + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void jButtonThemVaoThuVienClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() > 0) {
            Book document = new Book(currentShowDocument);
            document.setPDF((java.net.URL) null);

            new ThemTaiLieuFrame(document, true).setVisible(true);
            this.dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JButton jbuttonMuonTra;
    private JButton jbuttonDoc;
    private JPanel buttonPanel;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JPanel infoPanel;
    private JPanel mainPanel;
    
}
