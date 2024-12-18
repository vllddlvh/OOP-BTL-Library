package view;

import controller.UpdateTableTaiLieu;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import model.dao.FileFormatException;
import model.entity.Book;


public class ThemTaiLieuFrame extends javax.swing.JFrame {

    private Book emptyForm = null;
    private Image defaultImage = null;
    private URL defaultPDF = null;
    private boolean isNewBook;
    
    public ThemTaiLieuFrame() {
        try {
            emptyForm = new Book(""); 
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        isNewBook = true;
        initComponents();
        
        // Set phần tóm tắt tự xuống dòng và có thanh cuộn
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jTextAreaTomTat.setLineWrap(true);
        jTextAreaTomTat.setWrapStyleWord(true);
        jTextAreaTomTat.setPreferredSize(new Dimension(232, 84));
        jTextAreaTomTat.setMaximumSize(new Dimension(232, 84));
        jScrollPane2.setPreferredSize(new Dimension(232, 84));
        jScrollPane2.setMaximumSize(new Dimension(232, 84));
        
        this.setLocationRelativeTo(null);
        clearInputFields();
    }
    
    public ThemTaiLieuFrame(Book document , boolean isNewBook) {
        this.isNewBook = isNewBook;
        initComponents();
        this.setLocationRelativeTo(null);
        jTextFieldBookID.setEditable(false);
        
        try {
            defaultImage = document.getCover();
            defaultPDF = document.getPDF();
        } catch (IOException | SQLException | FileFormatException ex) {
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        emptyForm = new Book(document);
        clearInputFields();
    }

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
        jlbLanguage = new javax.swing.JLabel();
        jlbSummary = new javax.swing.JLabel();
        jTextFieldLanguage = new javax.swing.JTextField();
        jlbImage = new javax.swing.JLabel();
        jButtonChonFileAnh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaTomTat = new javax.swing.JTextArea();
        demoImageLabel = new javax.swing.JLabel();
        jButtonChonFilePDF = new javax.swing.JButton();
        jblPDF = new javax.swing.JLabel();
        jlbPdfPath = new javax.swing.JLabel();
        jlbImagePath = new javax.swing.JLabel();
        ButtonDelete = new javax.swing.JButton();
        ButtonSaveBook = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpnViewThemTaiLieu.setBackground(new java.awt.Color(214, 239, 216));

        jpnThemTaiLieu.setBackground(new java.awt.Color(128, 175, 129));
        jpnThemTaiLieu.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin tài liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jpnThemTaiLieu.setForeground(new java.awt.Color(255, 255, 255));
        jpnThemTaiLieu.setName(""); // NOI18N

        jlbID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbID.setForeground(new java.awt.Color(255, 255, 255));
        jlbID.setText("ID:         ");

        jlbTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbTitle.setForeground(new java.awt.Color(255, 255, 255));
        jlbTitle.setText("Tiêu đề:");

        jlbAuthor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbAuthor.setForeground(new java.awt.Color(255, 255, 255));
        jlbAuthor.setText("Tác giả:");

        jlbCategory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbCategory.setForeground(new java.awt.Color(255, 255, 255));
        jlbCategory.setText("Thể loại:");

        jlbPublicationYear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbPublicationYear.setForeground(new java.awt.Color(255, 255, 255));
        jlbPublicationYear.setText("Năm xuất bản:");

        jlbPublisher.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbPublisher.setForeground(new java.awt.Color(255, 255, 255));
        jlbPublisher.setText("Nhà xuất bản:");

        ButtonReset.setBackground(new java.awt.Color(80, 141, 78));
        ButtonReset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButtonReset.setForeground(new java.awt.Color(255, 255, 255));
        ButtonReset.setText("Reset");
        ButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonResetActionPerformed(evt);
            }
        });

        jTextFieldBookTitle.setMaximumSize(new java.awt.Dimension(64, 22));

        jTextFieldBookID.setMaximumSize(new java.awt.Dimension(64, 22));

        jTextFieldBookAuthor.setMaximumSize(new java.awt.Dimension(64, 22));

        jTextFieldBookPublisher.setMaximumSize(new java.awt.Dimension(64, 22));

        jTextFieldPublicationYear.setMaximumSize(new java.awt.Dimension(64, 22));

        jTextFieldCategory.setMaximumSize(new java.awt.Dimension(64, 22));

        jlbLanguage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbLanguage.setForeground(new java.awt.Color(255, 255, 255));
        jlbLanguage.setText("Ngôn ngữ:");

        jlbSummary.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbSummary.setForeground(new java.awt.Color(255, 255, 255));
        jlbSummary.setText("Tóm tắt:");

        jTextFieldLanguage.setMaximumSize(new java.awt.Dimension(64, 22));

        jlbImage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbImage.setForeground(new java.awt.Color(255, 255, 255));
        jlbImage.setText("Hình ảnh:");

        jButtonChonFileAnh.setBackground(new java.awt.Color(204, 204, 204));
        jButtonChonFileAnh.setText("Chọn file ảnh");
        jButtonChonFileAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChonFileAnhActionPerformed(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(234, 85));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(232, 84));
        jScrollPane2.setViewportView(null);

        jTextAreaTomTat.setColumns(20);
        jTextAreaTomTat.setRows(5);
        jTextAreaTomTat.setMaximumSize(new java.awt.Dimension(232, 84));
        jTextAreaTomTat.setMinimumSize(new java.awt.Dimension(232, 84));
        jScrollPane2.setViewportView(jTextAreaTomTat);

        demoImageLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 153, 153), null, null));

        jButtonChonFilePDF.setBackground(new java.awt.Color(204, 204, 204));
        jButtonChonFilePDF.setText("Chọn file PDF");
        jButtonChonFilePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChonFilePDFActionPerformed(evt);
            }
        });

        jblPDF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jblPDF.setForeground(new java.awt.Color(255, 255, 255));
        jblPDF.setText("Nội dung:");
        jblPDF.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jlbPdfPath.setText("jLabel1");
        jlbPdfPath.setToolTipText("");

        jlbImagePath.setText("jLabel1");
        jlbImagePath.setToolTipText("");

        ButtonDelete.setBackground(new java.awt.Color(80, 141, 78));
        ButtonDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        ButtonDelete.setText("Xóa sách");
        ButtonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnThemTaiLieuLayout = new javax.swing.GroupLayout(jpnThemTaiLieu);
        jpnThemTaiLieu.setLayout(jpnThemTaiLieuLayout);
        jpnThemTaiLieuLayout.setHorizontalGroup(
            jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jlbLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbAuthor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbPublisher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbPublicationYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jlbSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(95, Short.MAX_VALUE))
                    .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldLanguage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jTextFieldCategory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldPublicationYear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldBookID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldBookTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldBookAuthor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldBookPublisher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonChonFilePDF)
                                    .addComponent(jlbImage)
                                    .addComponent(jButtonChonFileAnh)
                                    .addComponent(jlbImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jblPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(demoImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jlbPdfPath, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))))
        );
        jpnThemTaiLieuLayout.setVerticalGroup(
            jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldBookID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbImage, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThemTaiLieuLayout.createSequentialGroup()
                                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlbAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldBookAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThemTaiLieuLayout.createSequentialGroup()
                                .addComponent(jButtonChonFileAnh)
                                .addGap(83, 83, 83)))
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldBookPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jblPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbPublicationYear, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPublicationYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonChonFilePDF))
                        .addGap(15, 15, 15)
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbPdfPath, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(demoImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ButtonDelete)
                        .addGap(33, 33, 33)
                        .addComponent(ButtonReset))
                    .addGroup(jpnThemTaiLieuLayout.createSequentialGroup()
                        .addGroup(jpnThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
            .addGroup(jpnViewThemTaiLieuLayout.createSequentialGroup()
                .addComponent(jpnThemTaiLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewThemTaiLieuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonSaveBook)
                .addGap(37, 37, 37))
        );
        jpnViewThemTaiLieuLayout.setVerticalGroup(
            jpnViewThemTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewThemTaiLieuLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(ButtonSaveBook, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
       clearInputFields();
    }//GEN-LAST:event_ButtonResetActionPerformed

    private void ButtonSaveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSaveBookActionPerformed

        String id = jTextFieldBookID.getText();
        String title = jTextFieldBookTitle.getText();
        String author = jTextFieldBookAuthor.getText();
        String publisher = jTextFieldBookPublisher.getText();
        int publicationYear;
        String category = jTextFieldCategory.getText();
        String language = jTextFieldLanguage.getText();
        String summary = jTextAreaTomTat.getText();

        
        if (id.isEmpty() || title.isEmpty() || author.isEmpty() || jTextFieldPublicationYear.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                                "Vui lòng điền đầy đủ thông tin bắt buộc!", 
                                                "Thông báo", 
                                            JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            publicationYear = Integer.parseInt(jTextFieldPublicationYear.getText());
        } catch (java.lang.NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                                                "Năm xuất bản sai định dạng.\nHãy nhập năm xuất bản gồm 4 chữ số", 
                                                "Thông báo", 
                                            JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
          
            emptyForm.setID(id);
            emptyForm.setTitle(title);
            emptyForm.setAuthor(author);
            emptyForm.setPublisher(publisher);
            emptyForm.setReleaseYear(publicationYear);
            emptyForm.setCategory(category);
            emptyForm.setDescription(summary);
            emptyForm.setLanguage(language);
            
         
            boolean isSaved = false;
            String msg;
            if (isNewBook) {
                isSaved = UpdateTableTaiLieu.getInstance().addElement(emptyForm);
                if (isSaved) {
                    msg = "Thêm tài liệu mới thành công!";
                } else {
                    msg = "Thêm tài liệu mới không thành công!\n Có thể tài liệu này đã có sẵn trong thư viện.";
                }
            } else {
                isSaved = UpdateTableTaiLieu.getInstance().updateElement(emptyForm);
                if (isSaved) {
                    msg = "Sửa tài liệu thành công!";
                } else {
                    msg = "Sửa tài liệu không thành công!";
                }
            }
            JOptionPane.showMessageDialog(this, 
                                                    msg, 
                                                     "Thông báo", 
                                                 JOptionPane.INFORMATION_MESSAGE);
            
            if (isSaved) {
                this.dispose();
            }
            
        } catch (SQLException | IOException | FileFormatException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.NullPointerException npt) {
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, npt);
            this.dispose();
        }
    }//GEN-LAST:event_ButtonSaveBookActionPerformed

    private void jButtonChonFileAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChonFileAnhActionPerformed
        
        try {
            File cover = ChonFileAnhJFrame.openWindowFileChooser();
            emptyForm.setCover(cover);
            
            displayImage(emptyForm.getCover());
            
        } catch (FileFormatException | IOException | SQLException ex) {
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        
        }
    }//GEN-LAST:event_jButtonChonFileAnhActionPerformed

    private void jButtonChonFilePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChonFilePDFActionPerformed
        
        try {
            File pdf = ChonFileAnhJFrame.openWindowFileChooser();
            if (pdf == null){
                return;
            }
            emptyForm.setPDF(pdf);
            
            jlbPdfPath.setText(pdf.getAbsolutePath());
            displayImage(emptyForm.getCover());
            
        } catch (FileFormatException | IOException | SQLException ex) {
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            
        }
    }//GEN-LAST:event_jButtonChonFilePDFActionPerformed

    private void ButtonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonDeleteMouseClicked
        try {
            UpdateTableTaiLieu.getInstance().deleteElement(emptyForm);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ButtonDeleteMouseClicked

    private void displayImage(Image img) {
        if (img == null) {
            return;
        }
        
        Image scaledImage = img.getScaledInstance(180, 250, Image.SCALE_SMOOTH);
        demoImageLabel.setIcon(new ImageIcon(scaledImage));
        if (img != defaultImage) {
            jlbImagePath.setText("Đã thay đổi ảnh bìa");
        }
    }
    
    private void clearInputFields() {
        jTextAreaTomTat.setText(emptyForm.getDescription());
        jTextFieldBookAuthor.setText(emptyForm.getAuthor());
        jTextFieldBookID.setText(emptyForm.getID());
        jTextFieldBookPublisher.setText(emptyForm.getPublisher());
        jTextFieldBookTitle.setText(emptyForm.getTitle());
        jTextFieldCategory.setText(emptyForm.getCategory());
        jTextFieldLanguage.setText(emptyForm.getLanguage());
        jTextFieldPublicationYear.setText(String.valueOf(emptyForm.getReleaseYear()));
        
        emptyForm.setCover(defaultImage, null);
        emptyForm.setPDF(defaultPDF); 
        
        
        displayImage(defaultImage);
        
        try {
            jlbImagePath.setText("Ảnh bìa ban đầu");
        
            if (emptyForm.getPDF() == null) {
                jlbPdfPath.setText("Không có file nội dung");
            } else {
                jlbPdfPath.setText(URLDecoder.decode(emptyForm.getPDF().toURI().getPath(), "UTF-8").substring(1));
            }
        } catch (IOException | SQLException | URISyntaxException ex) {
            Logger.getLogger(ThemTaiLieuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonReset;
    private javax.swing.JButton ButtonSaveBook;
    private javax.swing.JLabel demoImageLabel;
    private javax.swing.JButton jButtonChonFileAnh;
    private javax.swing.JButton jButtonChonFilePDF;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaTomTat;
    private javax.swing.JTextField jTextFieldBookAuthor;
    private javax.swing.JTextField jTextFieldBookID;
    private javax.swing.JTextField jTextFieldBookPublisher;
    private javax.swing.JTextField jTextFieldBookTitle;
    private javax.swing.JTextField jTextFieldCategory;
    private javax.swing.JTextField jTextFieldLanguage;
    private javax.swing.JTextField jTextFieldPublicationYear;
    private javax.swing.JLabel jblPDF;
    private javax.swing.JLabel jlbAuthor;
    private javax.swing.JLabel jlbCategory;
    private javax.swing.JLabel jlbID;
    private javax.swing.JLabel jlbImage;
    private javax.swing.JLabel jlbImagePath;
    private javax.swing.JLabel jlbLanguage;
    private javax.swing.JLabel jlbPdfPath;
    private javax.swing.JLabel jlbPublicationYear;
    private javax.swing.JLabel jlbPublisher;
    private javax.swing.JLabel jlbSummary;
    private javax.swing.JLabel jlbTitle;
    private javax.swing.JPanel jpnThemTaiLieu;
    private javax.swing.JPanel jpnViewThemTaiLieu;
    // End of variables declaration//GEN-END:variables
}
