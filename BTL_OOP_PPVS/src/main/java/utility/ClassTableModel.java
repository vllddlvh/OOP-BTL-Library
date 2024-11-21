package utility;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Member;

public class ClassTableModel {
    
    public DefaultTableModel setTableMember(List<Member> listItem, String[] listColumn) {
    DefaultTableModel dtm = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa
        }
    };

    dtm.setColumnIdentifiers(listColumn);
    for (Member member : listItem) {
        Object[] obj = new Object[5]; // Số lượng cột trong listColumn
        obj[0] = member.getID();
        obj[1] = member.getFirstName();
        obj[2] = member.getLastName();
        obj[3] = member.getContact();
        obj[4] = member.getDateOfBirth(); // Hiển thị ngày sinh
        dtm.addRow(obj);
    }

    return dtm;
}

}
