package view;

import bean.DanhMucBean;
import controller.ChuyenManHinhController;
import java.util.ArrayList;
import java.util.List;


public class BanDocJPanel extends javax.swing.JPanel {

    /**
     * Creates new form BanDocJPanel
     */
    public BanDocJPanel() {
        initComponents();
        ChuyenManHinhController controller = new ChuyenManHinhController(jpnViewBanDoc);
        List<DanhMucBean> listItem = new ArrayList<>();
        listItem.add(new DanhMucBean("MuonTraTaiLieu",jpnMuonTraTaiLieu,jlbMuonTraTaiLieu));
        listItem.add(new DanhMucBean("ThongTinThanhVien",jpnThongTinThanhVien,jlbThongTinThanhVien));
        
        controller.setEvent(listItem);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnViewBanDoc = new javax.swing.JPanel();
        jpnMuonTraTaiLieu = new javax.swing.JPanel();
        jlbMuonTraTaiLieu = new javax.swing.JLabel();
        jpnThongTinThanhVien = new javax.swing.JPanel();
        jlbThongTinThanhVien = new javax.swing.JLabel();

        jpnViewBanDoc.setBackground(new java.awt.Color(128, 175, 129));

        jpnMuonTraTaiLieu.setBackground(new java.awt.Color(80, 141, 78));

        jlbMuonTraTaiLieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbMuonTraTaiLieu.setForeground(new java.awt.Color(255, 255, 255));
        jlbMuonTraTaiLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoMuonTraTaiLieu.png"))); // NOI18N
        jlbMuonTraTaiLieu.setText("Mượn/Trả Tài Liệu");
        jlbMuonTraTaiLieu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlbMuonTraTaiLieu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jpnMuonTraTaiLieuLayout = new javax.swing.GroupLayout(jpnMuonTraTaiLieu);
        jpnMuonTraTaiLieu.setLayout(jpnMuonTraTaiLieuLayout);
        jpnMuonTraTaiLieuLayout.setHorizontalGroup(
            jpnMuonTraTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnMuonTraTaiLieuLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jlbMuonTraTaiLieu)
                .addGap(50, 50, 50))
        );
        jpnMuonTraTaiLieuLayout.setVerticalGroup(
            jpnMuonTraTaiLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnMuonTraTaiLieuLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jlbMuonTraTaiLieu)
                .addGap(50, 50, 50))
        );

        jpnThongTinThanhVien.setBackground(new java.awt.Color(80, 141, 78));

        jlbThongTinThanhVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbThongTinThanhVien.setForeground(new java.awt.Color(255, 255, 255));
        jlbThongTinThanhVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoThongTinThanhVien.png"))); // NOI18N
        jlbThongTinThanhVien.setText("Thông Tin Thành Viên");
        jlbThongTinThanhVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlbThongTinThanhVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jpnThongTinThanhVienLayout = new javax.swing.GroupLayout(jpnThongTinThanhVien);
        jpnThongTinThanhVien.setLayout(jpnThongTinThanhVienLayout);
        jpnThongTinThanhVienLayout.setHorizontalGroup(
            jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThongTinThanhVienLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jlbThongTinThanhVien)
                .addGap(50, 50, 50))
        );
        jpnThongTinThanhVienLayout.setVerticalGroup(
            jpnThongTinThanhVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongTinThanhVienLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jlbThongTinThanhVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpnViewBanDocLayout = new javax.swing.GroupLayout(jpnViewBanDoc);
        jpnViewBanDoc.setLayout(jpnViewBanDocLayout);
        jpnViewBanDocLayout.setHorizontalGroup(
            jpnViewBanDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnViewBanDocLayout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(jpnMuonTraTaiLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jpnThongTinThanhVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jpnViewBanDocLayout.setVerticalGroup(
            jpnViewBanDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnViewBanDocLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addGroup(jpnViewBanDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnMuonTraTaiLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnThongTinThanhVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(200, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewBanDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnViewBanDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jlbMuonTraTaiLieu;
    private javax.swing.JLabel jlbThongTinThanhVien;
    private javax.swing.JPanel jpnMuonTraTaiLieu;
    private javax.swing.JPanel jpnThongTinThanhVien;
    private javax.swing.JPanel jpnViewBanDoc;
    // End of variables declaration//GEN-END:variables
}
