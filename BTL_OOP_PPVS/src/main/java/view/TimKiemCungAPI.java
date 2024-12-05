package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.dao.FileFormatException;
import model.entity.Book;


public class TimKiemCungAPI extends javax.swing.JPanel {
    private List<Book> documents;
    public static TimKiemCungAPI newest;
    
    public TimKiemCungAPI() {
        newest = this;
        initComponents();
        // Sử dụng GridLayout cho jPanelBook
        jPanelBook.setLayout(new GridLayout(0, 4, 10, 10)); // 4 cột, khoảng cách 10px giữa các phần tử
        // Không cố định kích thước của jPanelBook và jScrollPaneBook
        // Để chúng tự động điều chỉnh theo không gian có sẵn.
        
        documents = new LinkedList<>();
        
        // Cập nhật dữ liệu và hiển thị tài liệu
        jScrollPaneBook.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPaneBook.getHorizontalScrollBar().setUnitIncrement(15);
        
    
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
        List<Book> filteredDocuments = new ArrayList<>(); // Danh sách lưu tài liệu lọc được

        for (Book document : documents) {
            // Kiểm tra nếu từ khóa xuất hiện trong bất kỳ trường nào của tài liệu
            if (document.getTitle().toLowerCase().contains(keyword) || // Tên tài liệu
                document.getAuthor().toLowerCase().contains(keyword)) { // Tác giả
                filteredDocuments.add(document); // Thêm tài liệu vào danh sách lọc
            }
        }

        try {
            // Hiển thị lại danh sách tài liệu đã lọc
            displayDocuments(filteredDocuments);
            
        } catch (IOException | SQLException | FileFormatException ex) {
            Logger.getLogger(TimKiemCungAPI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
}

    public void displaySingleDocument(Book document) throws IOException, SQLException, FileFormatException {
        JPanel documentCard = createDocumentCard(document);

        // Đặt kích thước cố định cho mỗi thẻ (200x300)
        documentCard.setPreferredSize(new Dimension(200, 300));

        // Thêm thẻ vào jPanelBook
        jPanelBook.add(documentCard);
        jPanelBook.revalidate();
        jPanelBook.repaint();
    }

    
    private void displayDocuments(List<Book> documents) throws IOException, SQLException, FileFormatException {
        jPanelBook.removeAll(); // Xóa nội dung cũ
        for (Book document : documents) {
            JPanel documentCard = createDocumentCard(document);

            // Đặt kích thước cố định cho mỗi thẻ (200x300)
            documentCard.setPreferredSize(new Dimension(200, 300));

            // Thêm thẻ vào jPanelBook
            jPanelBook.add(documentCard);
            jPanelBook.revalidate();
            jPanelBook.repaint();
        }
    }

private JPanel createDocumentCard(Book document) throws IOException, SQLException, FileFormatException {
    JPanel card = new JPanel();
    card.setLayout(new BorderLayout(5, 5)); // Khoảng cách giữa các thành phần
    card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

    // Image document
    JPanel image = new JPanel();
    image.setBackground(new Color(128, 175, 129));
    JLabel imageLabel = new JLabel();
    
    // Lấy file ảnh, nạp vào label
    Image coverImage = document.getCover();
    if (coverImage == null) {
        coverImage = ImageIO.read(new File("src\\main\\java\\image\\default-null-book-cover.png"));
    }
    
    Image scaledImage = coverImage.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
    imageLabel.setIcon(new ImageIcon(scaledImage));
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
    image.add(imageLabel);

    // Thông tin sách
    JTextArea infoArea = new JTextArea(document.getTitle() + "\nTác giả: " + document.getAuthor() + "\nNăm: " + document.getReleaseYear());
    infoArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
    infoArea.setForeground(Color.BLACK);
    infoArea.setEditable(false);
    infoArea.setLineWrap(true);
    infoArea.setWrapStyleWord(true);

    // Thêm các thành phần vào thẻ
    card.add(image, BorderLayout.NORTH);
    card.add(infoArea, BorderLayout.SOUTH);
    
    JPanel wrapper = new JPanel();
    wrapper.setBackground(new Color(128, 175, 129));
    wrapper.add(card);

    wrapper.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            new ChiTietTaiLieu(document).setVisible(true);
        }
    });

    return wrapper;
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
        buttonAPI = new javax.swing.JButton();

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

        buttonAPI.setText("Tìm kiếm với API");
        buttonAPI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonAPIMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTimKiemLayout = new javax.swing.GroupLayout(jPanelTimKiem);
        jPanelTimKiem.setLayout(jPanelTimKiemLayout);
        jPanelTimKiemLayout.setHorizontalGroup(
            jPanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTimKiemLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(JTextFieldTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(buttonAPI, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanelTimKiemLayout.setVerticalGroup(
            jPanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTimKiemLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTextFieldTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAPI, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
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

    private void buttonAPIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonAPIMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() > 0) {
            String keyword = JTextFieldTimKiem.getText().trim();
            if (keyword.length() > 0) {
                documents = new ArrayList<>();
                apiGoogleBook.APIConnector.searchBook(keyword);
            }
        }
    }//GEN-LAST:event_buttonAPIMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldTimKiem;
    private javax.swing.JButton buttonAPI;
    private javax.swing.JPanel jPanelBook;
    private javax.swing.JPanel jPanelTT;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JScrollPane jScrollPaneBook;
    // End of variables declaration//GEN-END:variables
}
