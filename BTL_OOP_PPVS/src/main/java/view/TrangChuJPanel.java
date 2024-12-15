package view;

import controller.UpdateTableTaiLieu;
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

/**
 * Class đại diện cho giao diện chính của ứng dụng (Trang chủ).
 */
public class TrangChuJPanel extends javax.swing.JPanel {
    private UpdateTableTaiLieu ctrl;
    private List<Book> documents;
    private static TrangChuJPanel instance = new TrangChuJPanel();
    
    /**
     * Lấy instance duy nhất của lớp TrangChuJPanel.
     * @return TrangChuJPanel instance duy nhất.
     */
    public static TrangChuJPanel getInstance() {
        reloadContents();
        return instance;
    }
    
    /**
     * Tải lại nội dung của panel.
     */
    public static void reloadContents() {
        try {
            instance.displayDocuments(instance.documents);
            
        } catch (IOException | SQLException | FileFormatException ex) {
            Logger.getLogger(TrangChuJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    /**
     * Constructor của TrangChuJPanel.
     */
    private TrangChuJPanel() {
        initComponents();
        jPanelBook.setLayout(new GridLayout(0, 4, 10, 10));
        
        try {
            ctrl = UpdateTableTaiLieu.getInstance();
            documents = ctrl.getListElement();
            
        } catch (IOException | SQLException ex) {
            Logger.getLogger(TrangChuJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        
        jScrollPaneBook.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPaneBook.getHorizontalScrollBar().setUnitIncrement(15);
    
        try {
            displayDocuments(documents);
            
        } catch (IOException | SQLException | FileFormatException ex) {
            Logger.getLogger(TrangChuJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    
        JTextFieldTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterDocuments();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterDocuments();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterDocuments();
            }
        });
    }

    /**
     * Lọc danh sách tài liệu theo từ khóa tìm kiếm.
     */
    private void filterDocuments() {
        String keyword = JTextFieldTimKiem.getText().toLowerCase().trim();
        List<Book> filteredDocuments = new ArrayList<>();
        for (Book document : documents) {
            if (document.getTitle().toLowerCase().contains(keyword) ||
                document.getAuthor().toLowerCase().contains(keyword)) {
                filteredDocuments.add(document);
            }
        }

        try {
            displayDocuments(filteredDocuments);
            
        } catch (IOException | SQLException | FileFormatException ex) {
            Logger.getLogger(TrangChuJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Hiển thị danh sách tài liệu lên giao diện.
     * @param documents Danh sách tài liệu cần hiển thị.
     * @throws IOException Khi gặp lỗi đọc file.
     * @throws SQLException Khi gặp lỗi truy vấn cơ sở dữ liệu.
     * @throws FileFormatException Khi định dạng file không hợp lệ.
     */
    private void displayDocuments(List<Book> documents) throws IOException, SQLException, FileFormatException {
        jPanelBook.removeAll();
        for (Book document : documents) {
            JPanel documentCard = createDocumentCard(document);
            documentCard.setPreferredSize(new Dimension(200, 340));
            jPanelBook.add(documentCard);
        }
        jPanelBook.revalidate();
        jPanelBook.repaint();
    }

    /**
     * Tạo một thẻ giao diện cho từng tài liệu.
     * @param document Tài liệu cần tạo thẻ.
     * @return JPanel chứa giao diện của tài liệu.
     * @throws IOException Khi gặp lỗi đọc file ảnh.
     * @throws SQLException Khi gặp lỗi truy vấn cơ sở dữ liệu.
     * @throws FileFormatException Khi định dạng file không hợp lệ.
     */
    private JPanel createDocumentCard(Book document) throws IOException, SQLException, FileFormatException {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JPanel image = new JPanel();
        image.setBackground(new Color(128, 175, 129));
        JLabel imageLabel = new JLabel();
        
        Image coverImage = document.getCover();
        if (coverImage == null) {
            coverImage = ImageIO.read(new File("src\\main\\java\\image\\default-null-book-cover.png"));
        }
        
        Image scaledImage = coverImage.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
            
        image.add(imageLabel);

        JTextArea infoArea = new JTextArea(document.getTitle() + 
                                            "\nTác giả: " + document.getAuthor() +
                                            "\nThể loại: " + document.getCategory() +
                                            "\nNăm: " + document.getReleaseYear() +
                                            "\t         Ngôn ngữ: " + document.getLanguage());
        infoArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
        infoArea.setForeground(Color.BLACK);
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        card.add(image, BorderLayout.CENTER);
        card.add(infoArea, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChiTietTaiLieu(document, false).setVisible(true);
            }
        });

        JPanel wrapper = new JPanel();
        wrapper.setBackground(new Color(128, 175, 129));
        wrapper.add(card);

        return wrapper;
    }

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
                .addContainerGap(41, Short.MAX_VALUE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldTimKiem;
    private javax.swing.JPanel jPanelBook;
    private javax.swing.JPanel jPanelTT;
    private javax.swing.JPanel jPanelTimKiem;
    private javax.swing.JScrollPane jScrollPaneBook;
    // End of variables declaration//GEN-END:variables
}
