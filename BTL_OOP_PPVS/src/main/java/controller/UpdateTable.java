package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.dao.FileFormatException;

/**
 *
 * @author ADMIN
 */
public abstract class UpdateTable <T> {
    protected LinkedList<T> allElement;
    protected JButton jbtAdd;
    protected JTextField jtfSearch;
    protected DefaultTableModel tableModel;
    
    protected abstract List<T> getListElement() throws SQLException, IOException;
    
    public abstract void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException, IOException;
    
    public abstract boolean addElement(T newElement) throws SQLException, IOException, FileFormatException;
    
    public abstract boolean updateElement(T alter) throws SQLException, IOException, FileFormatException;
    
    public abstract boolean deleteElement(T alter) throws SQLException;
    
    protected abstract void addRow(T newElement);
    
    protected abstract void updateRow(T alter);
    
    protected abstract void deleteRow(T alter);
}
