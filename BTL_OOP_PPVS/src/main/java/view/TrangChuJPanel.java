package view;

import controller.UpdateDocumentTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.entity.Document;

public class TrangChuJPanel extends javax.swing.JPanel {

    public TrangChuJPanel() throws SQLException {
        initComponents();
        // Sử dụng GridLayout cho jPanelBook
    jPanelBook.setLayout(new GridLayout(0, 4, 10, 10)); // 4 cột, khoảng cách 10px giữa các phần tử
    // Không cố định kích thước của jPanelBook và jScrollPaneBook
    // Để chúng tự động điều chỉnh theo không gian có sẵn.

    // Cập nhật dữ liệu và hiển thị tài liệu
    UpdateDocumentTable ctrl = UpdateDocumentTable.getUpdateDocumentTable();
    List<Document> documents = ctrl.getAlldcms();
    displayDocuments(documents);
    }

    private void displayDocuments(List<Document> documents) {
        jPanelBook.removeAll(); // Xóa nội dung cũ
        for (Document document:documents) {
            JPanel documentCard = createDocumentCard(document);
        
        // Đặt kích thước cố định cho mỗi thẻ (200x250)
        documentCard.setPreferredSize(new java.awt.Dimension(150, 200));
        
        // Thêm thẻ vào jPanelBook
        jPanelBook.add(documentCard);
            
        }
        jPanelBook.revalidate();
        jPanelBook.repaint();
    }
    
    private JPanel createDocumentCard(Document document) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        //Image document
        JPanel image = new JPanel();
        image.setBackground(new Color(128,175,129));
        JLabel imageLabel = new JLabel();
        String pathImage = "/image/" + document.getFileImage();
        URL imageURL = getClass().getResource(pathImage);
        if (imageURL == null) {
        System.out.println("Ảnh không tìm thấy tại: " + pathImage);
        } else {
        imageLabel.setIcon(new ImageIcon(imageURL));
        // Căn giữa hình ảnh trong JLabel
        imageLabel.setHorizontalAlignment(JLabel.CENTER);  // Căn giữa theo chiều ngang
        imageLabel.setVerticalAlignment(JLabel.CENTER);    // Căn giữa theo chiều dọc
        imageLabel.setBackground(Color.WHITE);
        }

        image.add(imageLabel);
        // Thông tin sách
        JTextArea infoArea = new JTextArea(document.getTitle() + "\nTác giả: " + document.getAuthor() + "\nNăm: " + document.getPublicationYear());
        infoArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
//        infoArea.setBackground(new Color(214,239,216));
        infoArea.setForeground(Color.BLACK);
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        // Thêm các thành phần vào thẻ
        card.add(image, BorderLayout.CENTER);
        card.add(infoArea, BorderLayout.SOUTH);
        
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gioiThieuDocument(document);
            }
        });

        return card;
    }
    
    // Hiển thị thông tin chi tiết sách trong cửa sổ mới (JFrame)
    private void gioiThieuDocument(Document document) {
    JFrame gioiThieuDocumentJFrame = new JFrame("Chi Tiết Sách");
    gioiThieuDocumentJFrame.setSize(720, 500);
    gioiThieuDocumentJFrame.setLayout(new BorderLayout());

    // Panel chính
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    mainPanel.setBackground(new Color(214, 239, 216));

    // Panel hiển thị ảnh
    JPanel imagePanel = new JPanel();
    imagePanel.setBackground(Color.WHITE);
    JLabel imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setVerticalAlignment(JLabel.TOP);
    String pathImage = "/image/" + document.getFileImage();
    URL imageURL = getClass().getResource(pathImage);

    if (imageURL != null) {
        ImageIcon originalIcon = new ImageIcon(imageURL);
        Image scaledImage = originalIcon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    } else {
        System.out.println("Ảnh không tìm thấy tại: " + pathImage);
        imageLabel.setText("Không tìm thấy ảnh");
    }

    imagePanel.add(imageLabel);
    mainPanel.add(imagePanel, BorderLayout.WEST);

    // Panel hiển thị thông tin
    JPanel infoPanel = new JPanel();
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
        "Năm xuất bản: " + document.getPublicationYear() + "\n" +
        "Giới thiệu: " + document.getSummary()
    );
    documentJTextArea.setEditable(false);
    documentJTextArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    documentJTextArea.setLineWrap(true);
    documentJTextArea.setWrapStyleWord(true);
    documentJTextArea.setBackground(Color.WHITE);

    JScrollPane scrollPane = new JScrollPane(documentJTextArea);
    scrollPane.setBorder(null);

    infoPanel.add(scrollPane);
    mainPanel.add(infoPanel, BorderLayout.CENTER);

    // Thêm panel chính vào JFrame
    gioiThieuDocumentJFrame.add(mainPanel, BorderLayout.CENTER);

    gioiThieuDocumentJFrame.setLocationRelativeTo(null);
    gioiThieuDocumentJFrame.setVisible(true);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTT = new javax.swing.JPanel();
        jScrollPaneBook = new javax.swing.JScrollPane();
        jPanelBook = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        jPanelTT.setBackground(new java.awt.Color(128, 175, 129));
        jPanelTT.setLayout(new java.awt.BorderLayout());

        jScrollPaneBook.setBackground(new java.awt.Color(255, 255, 255));

        jPanelBook.setBackground(new java.awt.Color(128, 175, 129));

        javax.swing.GroupLayout jPanelBookLayout = new javax.swing.GroupLayout(jPanelBook);
        jPanelBook.setLayout(jPanelBookLayout);
        jPanelBookLayout.setHorizontalGroup(
            jPanelBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        jPanelBookLayout.setVerticalGroup(
            jPanelBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );

        jScrollPaneBook.setViewportView(jPanelBook);

        jPanelTT.add(jScrollPaneBook, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(128, 175, 129));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 831, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 88, Short.MAX_VALUE)
        );

        jPanelTT.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTT, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBook;
    private javax.swing.JPanel jPanelTT;
    private javax.swing.JScrollPane jScrollPaneBook;
    // End of variables declaration//GEN-END:variables
}
