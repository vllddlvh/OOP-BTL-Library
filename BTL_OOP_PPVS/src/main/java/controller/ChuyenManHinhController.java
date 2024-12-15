package controller;

import bean.DanhMucBean;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.TrangChuJPanel;
import java.util.List;
import view.*;

/**
 * Lớp ChuyenManHinhController chịu trách nhiệm điều khiển việc chuyển đổi giữa các màn hình 
 * dựa trên các sự kiện giao diện.
 */
public class ChuyenManHinhController {
    private JPanel root;
    private String kindSelected = "";
    private List<DanhMucBean> listItem = null;

    /**
     * Khởi tạo đối tượng ChuyenManHinhController.
     * @param jpnRoot JPanel gốc để chứa các màn hình con.
     */
    public ChuyenManHinhController(JPanel jpnRoot) {
        this.root = jpnRoot;
    }

    /**
     * Thiết lập màn hình mặc định ban đầu.
     * @param jpnItem JPanel hiển thị.
     * @param jlbItem JLabel hiển thị.
     */
    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "TrangChu";
        jpnItem.setBackground(new Color(26, 83, 25));
        jlbItem.setBackground(new Color(26, 83, 25));
        
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(TrangChuJPanel.getInstance());
        root.validate();
        root.repaint();
    }

    /**
     * Gán sự kiện chuyển màn hình cho các mục trong danh sách.
     * @param listItem Danh sách các DanhMucBean đại diện cho các mục chuyển đổi.
     */
    public void setEvent(List<DanhMucBean> listItem) {
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    /**
     * Lớp sự kiện xử lý các thao tác chuột trên các mục.
     */
    class LabelEvent implements MouseListener {
        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        /**
         * Khởi tạo sự kiện chuột cho một mục.
         * @param kind Loại màn hình được liên kết.
         * @param jpnItem JPanel đại diện cho mục.
         * @param jlbItem JLabel đại diện cho mục.
         */
        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = TrangChuJPanel.getInstance();
                    break;
                case "BanDoc":
                    node = new BanDocJPanel();
                    break;
                case "TaiLieu":
                    node = new TaiLieuPanel();
                    break;
                case "MuonTraTaiLieu":
                    node = new MuonTraTaiLieuJPanel();
                    break;
                case "ThongTinThanhVien":
                    node = new ThongTinThanhVienJPanel();
                    break;
                case "HoSoCuaToi":
                    node = new HoSoCuaToiPanel();
                    break;
                case "ThongTinCuaToi":
                    node = new ThongTinCuaToiJPanel();
                    break;
                case "SachDaMuon":
                    node = new SachDaMuonJPanel();
                    break;
                case "GoogleAPI":
                    node = new TimKiemCungAPI();
                    break;
                default:
                    node = TrangChuJPanel.getInstance();
            }

            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackgroud(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(26, 83, 25));
            jlbItem.setBackground(new Color(26, 83, 25));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(80, 141, 78));
                jlbItem.setBackground(new Color(80, 141, 78));
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(26, 83, 25));
            jlbItem.setBackground(new Color(26, 83, 25));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(80, 141, 78));
                jlbItem.setBackground(new Color(80, 141, 78));
            } else {
                jpnItem.setBackground(new Color(26, 83, 25));
                jlbItem.setBackground(new Color(26, 83, 25));
            }
        }
    }

    /**
     * Cập nhật màu sắc của các mục để phản ánh mục hiện tại được chọn.
     * @param kind Loại màn hình được chọn.
     */
    private void setChangeBackgroud(String kind) {
        for (DanhMucBean item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(26, 83, 25));
                item.getJlb().setBackground(new Color(26, 83, 25));
            } else {
                item.getJpn().setBackground(new Color(80, 141, 78));
                item.getJlb().setBackground(new Color(80, 141, 78));
            }
        }
    }
}
