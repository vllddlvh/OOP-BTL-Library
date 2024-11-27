package view;

import controller.LoginController;
import controller.UpdateDocumentTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.entity.Account;
import model.entity.Document;

public class TrangChuJPanel extends javax.swing.JPanel {
    private final UpdateDocumentTable ctrl = UpdateDocumentTable.getUpdateDocumentTable();
    private final List<Document> documents = ctrl.getAlldcms();
    private final Account acc = LoginController.getAcc();
    public TrangChuJPanel() throws SQLException {
        initComponents();
        // Sử dụng GridLayout cho jPanelBook
    jPanelBook.setLayout(new GridLayout(0, 4, 10, 10)); // 4 cột, khoảng cách 10px giữa các phần tử
    // Không cố định kích thước của jPanelBook và jScrollPaneBook
    // Để chúng tự động điều chỉnh theo không gian có sẵn.

    // Cập nhật dữ liệu và hiển thị tài liệu
    jScrollPaneBook.getVerticalScrollBar().setUnitIncrement(15);
    jScrollPaneBook.getHorizontalScrollBar().setUnitIncrement(15);
    
    displayDocuments(documents);
    
    JTextFieldTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        filterDocuments(); // Tìm kiếm mỗi khi người dùng nhập
    }

    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        filterDocuments(); // Tìm kiếm khi người dùng xóa ký tự
    }

    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        filterDocuments(); // Tìm kiếm khi văn bản thay đổi (dù lý do gì)
    }
});
    }

    private void filterDocuments() {
    String keyword = JTextFieldTimKiem.getText().toLowerCase().trim(); // Lấy từ khóa tìm kiếm và loại bỏ khoảng trắng đầu/cuối
    List<Document> filteredDocuments = new ArrayList<>(); // Danh sách lưu tài liệu lọc được

    for (Document document : documents) {
        // Kiểm tra nếu từ khóa xuất hiện trong bất kỳ trường nào của tài liệu
        if (document.getTitle().toLowerCase().contains(keyword) || // Tên tài liệu
            document.getAuthor().toLowerCase().contains(keyword)) { // Tác giả
            filteredDocuments.add(document); // Thêm tài liệu vào danh sách lọc
        }
    }

    // Hiển thị lại danh sách tài liệu đã lọc
    displayDocuments(filteredDocuments);
}


    
    private void displayDocuments(List<Document> documents) {
    jPanelBook.removeAll(); // Xóa nội dung cũ
    for (Document document : documents) {
        JPanel documentCard = createDocumentCard(document);

        // Đặt kích thước cố định cho mỗi thẻ (200x300)
        documentCard.setPreferredSize(new Dimension(200, 300));

        // Thêm thẻ vào jPanelBook
        jPanelBook.add(documentCard);
    }
    jPanelBook.revalidate();
    jPanelBook.repaint();
}

private JPanel createDocumentCard(Document document) {
    JPanel card = new JPanel();
    card.setLayout(new BorderLayout(5, 5)); // Khoảng cách giữa các thành phần
    card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

    // Image document
    JPanel image = new JPanel();
    image.setBackground(new Color(128, 175, 129));
    JLabel imageLabel = new JLabel();
    String pathImage = "/image/" + document.getFileImage();
    URL imageURL = getClass().getResource(pathImage);
    if (imageURL == null) {
        System.out.println("Ảnh không tìm thấy tại: " + pathImage);
    } else {
        ImageIcon originalIcon = new ImageIcon(imageURL);
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
    }
    image.add(imageLabel);

    // Thông tin sách
    JTextArea infoArea = new JTextArea(document.getTitle() + "\nTác giả: " + document.getAuthor() + "\nNăm: " + document.getPublicationYear());
    infoArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
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
    JPanel imagePanel = new JPanel(new BorderLayout()); // Sử dụng BorderLayout
    imagePanel.setBackground(Color.WHITE);

    // Tạo JLabel cho ảnh
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

    // Tạo nút "Mượn sách"
    JButton jbuttonMuon = new JButton("Mượn sách");
    jbuttonMuon.setBackground(new Color(80, 141, 78));
    jbuttonMuon.setForeground(Color.WHITE);
    jbuttonMuon.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    jbuttonMuon.setFocusPainted(false);
    jbuttonMuon.setPreferredSize(new Dimension(100, 40)); // Đặt kích thước cho nút
    // Gán sự kiện cho nút
    jbuttonMuon.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Xử lý khi nhấn nút
        TuMuonSachJFrame tuMuonSach = new TuMuonSachJFrame(document,acc);
        tuMuonSach.setVisible(true);
    }
    });

    // Thêm ảnh và nút vào imagePanel
    imagePanel.add(imageLabel, BorderLayout.CENTER); // Ảnh nằm ở trung tâm
    imagePanel.add(jbuttonMuon, BorderLayout.SOUTH); // Nút nằm dưới
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
        jPanelTimKiem = new javax.swing.JPanel();
        JTextFieldTimKiem = new javax.swing.JTextField();

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

        jPanelTimKiem.setBackground(new java.awt.Color(128, 175, 129));

        JTextFieldTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextFieldTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTimKiemLayout = new javax.swing.GroupLayout(jPanelTimKiem);
        jPanelTimKiem.setLayout(jPanelTimKiemLayout);
        jPanelTimKiemLayout.setHorizontalGroup(
            jPanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTimKiemLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(JTextFieldTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(293, Short.MAX_VALUE))
        );
        jPanelTimKiemLayout.setVerticalGroup(
            jPanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTimKiemLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(JTextFieldTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jPanelTT.add(jPanelTimKiem, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTT, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void JTextFieldTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextFieldTimKiemActionPerformed
    
    }//GEN-LAST:event_JTextFieldTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldTimKiem;
    private javax.swing.JPanel jPanelBook;
    private javax.swing.JPanel jPanelTT;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JScrollPane jScrollPaneBook;
    // End of variables declaration//GEN-END:variables
}
