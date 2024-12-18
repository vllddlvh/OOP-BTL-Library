package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.entity.Book;
import model.dao.DocumentDAO;
import model.dao.FileFormatException;
import model.dao.FileHandle;
import view.ThemTaiLieuFrame;

/**
 * Quản lý cập nhật bảng hiển thị tài liệu sách của admin (JTable) đồng bộ với dữ liệu trong Database.
 */
public class UpdateTableTaiLieu extends UpdateTable<Book> {

    private static UpdateTableTaiLieu singleton = new UpdateTableTaiLieu();

    private UpdateTableTaiLieu() {}

    /**
     * Singleton: Lấy instance duy nhất của UpdateBookTable.
     * 
     * @return instance của UpdateBookTable
     */
    public static UpdateTableTaiLieu getInstance() {
        return singleton;
    }

    
    /**
     * Lấy toàn bộ tài liệu từ cơ sở dữ liệu.
     * 
     * @throws SQLException 
     */
    @Override
    public List<Book> getListElement() throws SQLException, IOException {
        allElement = DocumentDAO.getAllBook();
        return allElement;
    }
    
    /**
    * Thêm một đối tượng sách (Book) vào cơ sở dữ liệu và đồng thời cập nhật hiển thị trên bảng danh sách.
    * 
    * @param alter đối tượng sách cần thêm vào cơ sở dữ liệu.
    * @return {@code true} nếu thêm thành công, {@code false} nếu thêm thất bại.
    * 
    * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu.
    * @throws IOException nếu xảy ra lỗi nhập/xuất dữ liệu.
    * @throws model.dao.FileFormatException nếu định dạng dữ liệu không hợp lệ.
    * @throws URISyntaxException nếu URI của tài nguyên không đúng định dạng.
    */
    @Override
    public boolean addElement(Book newBook) throws SQLException, IOException, FileFormatException, URISyntaxException {
        if (DocumentDAO.addBook(newBook)) {
            allElement.add(newBook);
            addRow(newBook);
            return true;
        }
        return false;
    }

    /**
    * Chỉnh sửa thông tin của một đối tượng sách (Book) trong cơ sở dữ liệu và đồng thời cập nhật trên bảng danh sách.
    * 
    * @param alter đối tượng sách đã chỉnh sửa, chứa thông tin mới.
    * @return {@code true} nếu chỉnh sửa thành công, {@code false} nếu chỉnh sửa thất bại.
    * 
    * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu.
    * @throws IOException nếu xảy ra lỗi nhập/xuất dữ liệu.
    * @throws model.dao.FileFormatException nếu định dạng dữ liệu không hợp lệ.
    * @throws URISyntaxException nếu URI của tài nguyên không đúng định dạng.
    */
    @Override
    public boolean updateElement(Book alter) throws SQLException, IOException, FileFormatException, URISyntaxException {
        if (DocumentDAO.updateBook(alter)) {
            int i = 0;
            while (i < allElement.size()) {
                if (allElement.get(i).getID().equals(alter.getID())) {
                    allElement.set(i, new Book(alter));
                    
                    break;
                }
                i++;
            }
            updateRow(alter); // Cập nhật hiển thị trên bảng
            return true;
        }
        return false;
    }

    /**
    * Xóa một đối tượng sách (Book) khỏi cơ sở dữ liệu và cập nhật hiển thị trên bảng.
    * 
    * @param deleteBook sách cần xóa khỏi cơ sở dữ liệu và bảng.
    * @return {@code true} nếu xóa thành công.
    * 
    * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu.
    */
    @Override
    public boolean deleteElement(Book deleteBook) throws SQLException {
        
        DocumentDAO.deleteDocument(deleteBook.getID());

        // Xóa khỏi bảng hiển thị
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equals(deleteBook.getID())) {
                tableModel.removeRow(i);
                break;
            }
        }
        return true;
    }

    /**
    * Thêm một hàng mới vào bảng hiển thị (JTable).
    * 
    * @param newBook sách mới được thêm vào bảng hiển thị.
    */
    @Override
    protected void addRow(Book newBook) {
        String[] newRow = new String[7];
        newRow[0] = newBook.getID();
        newRow[1] = newBook.getTitle();
        newRow[2] = newBook.getAuthor();
        newRow[3] = newBook.getPublisher();
        newRow[4] = String.valueOf(newBook.getReleaseYear());
        newRow[5] = newBook.getCategory();
        newRow[6] = newBook.getLanguage();
        

        tableModel.addRow(newRow);
    }
    
    /**
    * Cập nhật một hàng trong bảng hiển thị (JTable) với thông tin mới.
    * 
    * @param alter sách đã được chỉnh sửa thông tin cần cập nhật vào bảng.
    */
    @Override
    protected void updateRow(Book alter) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (tableModel.getValueAt(row, 0).equals(alter.getID())) {
                
                tableModel.setValueAt(alter.getTitle(), row, 1);
                tableModel.setValueAt(alter.getAuthor(), row, 2);
                tableModel.setValueAt(alter.getPublisher(), row, 3);
                tableModel.setValueAt(alter.getReleaseYear(), row, 4);
                tableModel.setValueAt(alter.getCategory(), row, 5);
                tableModel.setValueAt(alter.getLanguage(), row, 6);

                // Còn availableCopies, description, publíher
                
                break;
            }
        }
    }

    /**
    * Xóa một hàng khỏi bảng hiển thị (JTable).
    * 
    * @param deletedOne sách cần xóa khỏi bảng.
    */
    @Override
    protected void deleteRow(Book deletedOne) {
        int n = tableModel.getRowCount();
        for (int i = 0; i < n; i++) {
            if (tableModel.getValueAt(i, 0).equals(deletedOne.getID())) {
                tableModel.removeRow(i);
                
                break;
            }
        }
    }
    

    /**
    * Thiết lập bảng hiển thị, nút "Add", và trường tìm kiếm để đồng bộ với dữ liệu cơ sở dữ liệu.
    * 
    * @param table bảng JTable cần đồng bộ dữ liệu.
    * @param jbtAdd nút thêm sách.
    * @param jtfSearch trường tìm kiếm sách.
    * 
    * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu.
    * @throws IOException nếu xảy ra lỗi nhập/xuất dữ liệu.
    */
    @Override
    public void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException, IOException {
        singleton.tableModel = (DefaultTableModel) table.getModel();
        singleton.jbtAdd = jbtAdd;
        singleton.jtfSearch = jtfSearch;

        // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào bảng
        getListElement();
        for (Book document : allElement) {
            addRow(document);
        }

        // Thiết lập bộ lọc tìm kiếm
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter(jtfSearch, rowSorter);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter(jtfSearch, rowSorter);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        // Xử lý sự kiện nhấp chuột
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    // Lấy thông tin từ hàng được chọn
                    Book book = null;
                    String targetID = table.getValueAt(row, 0).toString();
                    for (Book x : allElement) {
                        if (x.getID().equals(targetID)) {
                            book = x;
                            break;
                        }
                    }
                    
                    // Mở form chỉnh sửa tại đây (nếu có)
                    if (book != null) {
                        new ThemTaiLieuFrame(book, false).setVisible(true);
                    }
                }
            }
        });
    }

    /**
    * Cập nhật bộ lọc tìm kiếm của bảng dựa trên nội dung trong trường tìm kiếm.
    * 
    * @param jtfSearch trường tìm kiếm.
    * @param rowSorter bộ lọc của bảng.
    */
    private void updateFilter(JTextField jtfSearch, TableRowSorter<TableModel> rowSorter) {
        String text = jtfSearch.getText();
        if (text.trim().isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
}
