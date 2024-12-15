
package bean;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Lớp DanhMucBean đại diện cho một danh mục với thông tin liên quan như tên, 
 * panel hiển thị và label mô tả.
 */
public class DanhMucBean {

    private String kind;
    private JPanel jpn;
    private JLabel jlb;

    public DanhMucBean() {
    }

    /**
     * Constructor với tham số để khởi tạo đối tượng.
     * 
     * @param kind Loại danh mục (kiểu String).
     * @param jpn Panel hiển thị (kiểu JPanel).
     * @param jlb Label mô tả (kiểu JLabel).
     */
    public DanhMucBean(String kind, JPanel jpn, JLabel jlb) {
        this.kind = kind;
        this.jpn = jpn;
        this.jlb = jlb;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public JPanel getJpn() {
        return jpn;
    }

    public void setJpn(JPanel jpn) {
        this.jpn = jpn;
    }

    public JLabel getJlb() {
        return jlb;
    }

    public void setJlb(JLabel jlb) {
        this.jlb = jlb;
    }

}