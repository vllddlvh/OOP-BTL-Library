package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import controller.UpdateTableThongTinThanhVien;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * JPanel hiển thị thông tin thành viên và các thao tác liên quan.
 * 
 * Chức năng chính:
 * <ul>
 *   <li>Hiển thị danh sách thông tin thành viên trong bảng.</li>
 *   <li>Cho phép thêm mới thành viên.</li>
 *   <li>Tìm kiếm thông tin thành viên theo từ khóa.</li>
 * </ul>
 *
 * @author ADMIN
 */
public class ThongTinThanhVienJPanel extends javax.swing.JPanel {

    /**
     * Tạo một JPanel mới để hiển thị thông tin thành viên.
     */
    public ThongTinThanhVienJPanel() {
        initComponents();
        initTableThanhVien();
       
        UpdateTableThongTinThanhVien ctrl = UpdateTableThongTinThanhVien.getInstance();
        try {
            ctrl.setTableUpToDate(tableThanhVien, JButtonThemThanhVien, JTextFieldTimKiemThanhVien);
        } catch (SQLException ex) {
            Logger.getLogger(ThongTinThanhVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    /**
     * Khởi tạo bảng thông tin thành viên với các thuộc tính giao diện.
     */
    private void initTableThanhVien() {
        tableThanhVien.getTableHeader().setFont(new Font("Arrial", Font.BOLD, 14));
        tableThanhVien.getTableHeader().setForeground(Color.white);
        tableThanhVien.getTableHeader().setPreferredSize(new Dimension(100,50));
        tableThanhVien.getTableHeader().setBackground(new Color(80,141,78));
        tableThanhVien.setRowHeight(40);
        tableThanhVien.validate();
        tableThanhVien.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnViewThongTinThanhVien = new javax.swing.JPanel();
        JButtonThemThanhVien = new javax.swing.JButton();
        JTextFieldTimKiemThanhVien = new javax.swing.JTextField();
        tableScrollPanel = new javax.swing.JScrollPane();
        tableThanhVien = new javax.swing.JTable();

        jpnViewThongTinThanhVien.setBackground(new java.awt.Color(128, 175, 129));

        JButtonThemThanhVien.setBackground(new java.awt.Color(80, 141, 78));
        JButtonThemThanhVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JButtonThemThanhVien.setForeground(new java.awt.Color(255, 255, 255));
        JButtonThemThanhVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoAddMember (1).png"))); // NOI18N
        JButtonThemThanhVien.setText("Thêm thành viên");
        JButtonThemThanhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonThemThanhVienActionPerformed(evt);
            }
        });

        JTextFieldTimKiemThanhVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        JTextFieldTimKiemThanhVien.setForeground(new java.awt.Color(204, 204, 204));
        JTextFieldTimKiemThanhVien.setText("Tìm kiếm thông tin thành viên ");
        JTextFieldTimKiemThanhVien.setMinimumSize(new java.awt.Dimension(64, 22));
        JTextFieldTimKiemThanhVien.setPreferredSize(new java.awt.Dimension(178, 22));
        JTextFieldTimKiemThanhVien.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                JTextFieldTimKiemThanhVienFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                JTextFieldTimKiemThanhVienFocusLost(evt);
            }
        });
        JTextFieldTimKiemThanhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTextFieldTimKiemThanhVienActionPerformed(evt);
            }
        });

        tableThanhVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First Name", "Last Name", "Contact", "Date Of Birth"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableThanhVien.setToolTipText("");
        tableThanhVien.setColumnSelectionAllowed(true);
        tableScrollPanel.setViewportView(tableThanhVien);
        tableThanhVien.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableThanhVien.getColumnModel().getColumnCount() > 0) {
            tableThanhVien.getColumnModel().getColumn(0).setResizable(false);
            tableThanhVien.getColumnModel().getColumn(1).setResizable(false);
            tableThanhVien.getColumnModel().getColumn(2).setResizable(false);
            tableThanhVien.getColumnModel().getColumn(3).setResizable(false);
            tableThanhVien.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jpnViewThongTinThanhVienLayout = new javax.swing.GroupLayout(jpnViewThongTinThanhVien);
        jpnViewThongTinThanhVien.setLayout(jpnViewThongTinThanhVienLayout);
        jpnViewThongTinThanhVienLayout.setHorizontalGroup(
            jpnViewThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnViewThongTinThanhVienLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jpnViewThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tableScrollPanel)
                    .addGroup(jpnViewThongTinThanhVienLayout.createSequentialGroup()
                        .addComponent(JTextFieldTimKiemThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(JButtonThemThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jpnViewThongTinThanhVienLayout.setVerticalGroup(
            jpnViewThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnViewThongTinThanhVienLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpnViewThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTextFieldTimKiemThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JButtonThemThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(tableScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewThongTinThanhVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewThongTinThanhVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void JTextFieldTimKiemThanhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTextFieldTimKiemThanhVienActionPerformed

    }//GEN-LAST:event_JTextFieldTimKiemThanhVienActionPerformed

    /**
     * Xử lý sự kiện khi nhấn nút "Thêm thành viên".
     * 
     * @param evt Sự kiện được kích hoạt.
     */
    private void JButtonThemThanhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonThemThanhVienActionPerformed
        ThemThanhVienJFrame themThanhVienJFrame = new ThemThanhVienJFrame();
        themThanhVienJFrame.setVisible(true);
    }//GEN-LAST:event_JButtonThemThanhVienActionPerformed

    /**
     * Xử lý sự kiện khi trường tìm kiếm nhận được focus.
     * 
     * @param evt Sự kiện được kích hoạt.
     */
    private void JTextFieldTimKiemThanhVienFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JTextFieldTimKiemThanhVienFocusGained
        JTextFieldTimKiemThanhVien.setText("");
        JTextFieldTimKiemThanhVien.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        JTextFieldTimKiemThanhVien.setForeground(java.awt.Color.BLACK);
    }//GEN-LAST:event_JTextFieldTimKiemThanhVienFocusGained

    /**
     * Xử lý sự kiện khi trường tìm kiếm mất focus.
     * 
     * @param evt Sự kiện được kích hoạt.
     */
    private void JTextFieldTimKiemThanhVienFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JTextFieldTimKiemThanhVienFocusLost
        if(JTextFieldTimKiemThanhVien.getText().equals("")) {
            JTextFieldTimKiemThanhVien.setText("Tìm kiếm thông tin thành viên");
            JTextFieldTimKiemThanhVien.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            JTextFieldTimKiemThanhVien.setForeground(new Color(204,204,204));
        }
    }//GEN-LAST:event_JTextFieldTimKiemThanhVienFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButtonThemThanhVien;
    private javax.swing.JTextField JTextFieldTimKiemThanhVien;
    private javax.swing.JPanel jpnViewThongTinThanhVien;
    private javax.swing.JScrollPane tableScrollPanel;
    private javax.swing.JTable tableThanhVien;
    // End of variables declaration//GEN-END:variables
}
