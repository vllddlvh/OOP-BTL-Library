//package controller;
//
//import java.sql.SQLException;
//import javax.swing.JButton;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import model.dao.RequestDAO;
//import model.entity.Request;
//
//public class UpdateRequestTable extends UpdateTable<Request> {
//    private static final UpdateRequestTable singleT = new UpdateRequestTable();
//
//    private UpdateRequestTable() {}
//
//    public static UpdateRequestTable getMuonTraSachController() {
//        return singleT;
//    }
//
//    @Override
//    public void getListElement() throws SQLException {
//        allElement = RequestDAO.getAllRequest();
//    }
//
//    /**
//     * Muon sach.
//     * @param request thong tin.
//     * @return
//     * @throws SQLException 
//     */
//    @Override
//    public boolean addElement(Request request) throws SQLException {
//        if(RequestDAO.borrowDocument(request.getUserID(), request.getDocumentID())) {
//            allElement.add(request);
//            addRow(request);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Tra sach.
//     * @param updatedMember sach duoc tra.
//     * @return
//     * @throws SQLException 
//     */
//    @Override
//    public boolean updateElement(Request updatedMember) throws SQLException {
//        
//    }
//
//    @Override
//    public boolean deleteElement(Request deleteMember) throws SQLException {
//        return false;
//    }
//
//    @Override
//    public void updateRow(Request updatedMember) {
//        
//    }
//
//    @Override
//    protected void addRow(Request request) {
//       String[] newLine = new String[6]; // Số lượng cột trong listColumn
//        newLine[0] = request.getRequestID();
//        newLine[1] = request.getUserID();
//        newLine[2] = request.getDocumentID();
//        newLine[3] = "1";
//        newLine[4] = request.getBorrowDate();
//        newLine[5] = request.getReturnDate();
//            
//        tableModel.addRow(newLine);
//    }
//    
//    @Override
//    public void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException {
//       
//    }
//}
