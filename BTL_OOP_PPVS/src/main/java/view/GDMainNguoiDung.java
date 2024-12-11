package view;

import bean.DanhMucBean;
import controller.ChuyenManHinhController;
import java.util.List;
import java.util.ArrayList;

public class GDMainNguoiDung extends javax.swing.JFrame {

    public GDMainNguoiDung() {
        initComponents();
        
        setTitle("THƯ VIỆN");
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        ChuyenManHinhController controller = new ChuyenManHinhController(jpnView);
        controller.setView(jpnMHC, jlbMHC);
        List<DanhMucBean> listItem = new ArrayList<>();
        listItem.add(new DanhMucBean("TrangChu",jpnMHC,jlbMHC));
        listItem.add(new DanhMucBean("HoSoCuaToi",jpnHoSoCuaToi,jlbHoSoCuaToi));
        listItem.add(new DanhMucBean("GoogleAPI", jpnGGAPI, jlbGGAPI));
        
        controller.setEvent(listItem);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        jpnMenu = new javax.swing.JPanel();
        jlbThuVien = new javax.swing.JLabel();
        jpnMHC = new javax.swing.JPanel();
        jlbMHC = new javax.swing.JLabel();
        jpnHoSoCuaToi = new javax.swing.JPanel();
        jlbHoSoCuaToi = new javax.swing.JLabel();
        jpnGGAPI = new javax.swing.JPanel();
        jlbGGAPI = new javax.swing.JLabel();
        jpnView = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnRoot.setBackground(new java.awt.Color(214, 239, 216));
        jpnRoot.setMaximumSize(new java.awt.Dimension(1000, 500));
        jpnRoot.setMinimumSize(new java.awt.Dimension(720, 400));

        jpnMenu.setBackground(new java.awt.Color(80, 141, 78));

        jlbThuVien.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jlbThuVien.setForeground(new java.awt.Color(255, 255, 255));
        jlbThuVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbThuVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoBook.png"))); // NOI18N
        jlbThuVien.setText("THƯ VIỆN");

        javax.swing.GroupLayout jpnMenuLayout = new javax.swing.GroupLayout(jpnMenu);
        jpnMenu.setLayout(jpnMenuLayout);
        jpnMenuLayout.setHorizontalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMenuLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jlbThuVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(75, 75, 75))
        );
        jpnMenuLayout.setVerticalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbThuVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnMHC.setBackground(new java.awt.Color(80, 141, 78));
        jpnMHC.setAlignmentX(1.0F);
        jpnMHC.setAlignmentY(1.0F);

        jlbMHC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbMHC.setForeground(new java.awt.Color(255, 255, 255));
        jlbMHC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoMHC.png"))); // NOI18N
        jlbMHC.setText("Màn hình chính");
        jlbMHC.setIconTextGap(6);

        javax.swing.GroupLayout jpnMHCLayout = new javax.swing.GroupLayout(jpnMHC);
        jpnMHC.setLayout(jpnMHCLayout);
        jpnMHCLayout.setHorizontalGroup(
            jpnMHCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnMHCLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jlbMHC, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jpnMHCLayout.setVerticalGroup(
            jpnMHCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMHCLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jlbMHC)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jpnHoSoCuaToi.setBackground(new java.awt.Color(80, 141, 78));
        jpnHoSoCuaToi.setAlignmentX(1.0F);
        jpnHoSoCuaToi.setAlignmentY(1.0F);

        jlbHoSoCuaToi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbHoSoCuaToi.setForeground(new java.awt.Color(255, 255, 255));
        jlbHoSoCuaToi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbHoSoCuaToi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoPeople.png"))); // NOI18N
        jlbHoSoCuaToi.setText("Hồ sơ của tôi");
        jlbHoSoCuaToi.setIconTextGap(5);

        javax.swing.GroupLayout jpnHoSoCuaToiLayout = new javax.swing.GroupLayout(jpnHoSoCuaToi);
        jpnHoSoCuaToi.setLayout(jpnHoSoCuaToiLayout);
        jpnHoSoCuaToiLayout.setHorizontalGroup(
            jpnHoSoCuaToiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnHoSoCuaToiLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jlbHoSoCuaToi, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jpnHoSoCuaToiLayout.setVerticalGroup(
            jpnHoSoCuaToiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnHoSoCuaToiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jlbHoSoCuaToi)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jpnGGAPI.setBackground(new java.awt.Color(80, 141, 78));
        jpnGGAPI.setAlignmentX(1.0F);
        jpnGGAPI.setAlignmentY(1.0F);

        jlbGGAPI.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlbGGAPI.setForeground(new java.awt.Color(255, 255, 255));
        jlbGGAPI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/google-play-books-logo.png"))); // NOI18N
        jlbGGAPI.setText("Google API");
        jlbGGAPI.setIconTextGap(6);

        javax.swing.GroupLayout jpnGGAPILayout = new javax.swing.GroupLayout(jpnGGAPI);
        jpnGGAPI.setLayout(jpnGGAPILayout);
        jpnGGAPILayout.setHorizontalGroup(
            jpnGGAPILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnGGAPILayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jlbGGAPI, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jpnGGAPILayout.setVerticalGroup(
            jpnGGAPILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnGGAPILayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbGGAPI)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnRootLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpnMHC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpnRootLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jpnGGAPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpnHoSoCuaToi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addComponent(jpnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jpnMHC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jpnHoSoCuaToi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jpnGGAPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jpnView.setMaximumSize(new java.awt.Dimension(1000, 500));
        jpnView.setMinimumSize(new java.awt.Dimension(720, 400));

        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
        jpnView.setLayout(jpnViewLayout);
        jpnViewLayout.setHorizontalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 785, Short.MAX_VALUE)
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(GDMainNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GDMainNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GDMainNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GDMainNguoiDung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               new GDMainNguoiDung().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jlbGGAPI;
    private javax.swing.JLabel jlbHoSoCuaToi;
    private javax.swing.JLabel jlbMHC;
    private javax.swing.JLabel jlbThuVien;
    private javax.swing.JPanel jpnGGAPI;
    private javax.swing.JPanel jpnHoSoCuaToi;
    private javax.swing.JPanel jpnMHC;
    private javax.swing.JPanel jpnMenu;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnView;
    // End of variables declaration//GEN-END:variables
}
