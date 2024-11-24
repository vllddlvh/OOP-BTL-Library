package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public abstract class UpdateTable <T> {
    protected ArrayList<T> allElement;
    protected JButton jbtAdd;
    protected JTextField jtfSearch;
    protected DefaultTableModel tableModel;
    
    protected abstract void getListElement() throws SQLException;
    
    public abstract void setTableUpToDate(JTable table, JButton jbtAdd, JTextField jtfSearch) throws SQLException;
    
    public abstract boolean addElement(T newElement) throws SQLException;
    
    public abstract boolean updateElement(T updatedMember) throws SQLException;
    
    public abstract boolean deleteElement(T deleteMember) throws SQLException;
    
    public abstract void updateRow(T updatedMember);
    
    protected abstract void addRow(T newElement);
}
