package controller;


import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.CallableStatement;


import model.Book;
import java.util.ArrayList;

public class DBBook extends DatabaseConnector {
    
    /**
     * <p>
     * findBy option:<br>
     *     1. find by name<br>
     *     2. find by category<br>
     *     3. find by author<br>
     *     4. find by publisher<br>
     * <p>
     * <p>
     * sortBy option:<br>
     *      0. sort by insert index (else)<br>
	 *      1. sort by book's name ASC<br>
     *      2. sort by book's name DESC<br>
     *      3. sort by releaseDate ASC<br>
     *      4. sort by releaseDate DESC
     * <p>
     * 
     * @param keyword = words you need to find in the title.
     * @param searchBy = the type you want to search.
     * @param sortBy = the way of sort you want.
     * 
     * @return an ArrayList<Book> of what you need.
     * 
     * @throws SQLException 
     */
    public static ArrayList<Book> findBook(String keyword, int searchBy, int sortBy) throws SQLException {
        ResultSet rs;
        ArrayList<Book> found = new ArrayList<>();
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call findBook(?, ?, ?) }");
        
        finder.setString(1, keyword);
        finder.setString(2, String.valueOf(searchBy));
        finder.setString(3, String.valueOf(sortBy));
        
        rs = finder.executeQuery();
        
        while(rs.next()) {
            // String id, String name, String author, String category, String publishDate, String publisher, int quantity
            found.add(new Book(rs.getString("bookID"), rs.getString("b_name"), rs.getString("author"), rs.getString("category"), rs.getString("publishDate"), rs.getString("publisher"), rs.getInt("quantity")));
        }
        return found;
    }
    
    
    /**
     * Find a book by its ID.
     * 
     * @param bookID = id of the book you want to find.
     * 
     * @return a Book object that you want to find.
     * 
     * @throws SQLException 
     */
    public static Book findBook(String bookID) throws SQLException {
        ResultSet rs;
        Book found;
        
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call findBookFromID(?) }");
        
        finder.setString(1, bookID);
        
        rs = finder.executeQuery();
        
        while (rs.next()) {
            // String name, String author, String category, String publishDate, String publisher, int quantity
            found = new Book(rs.getString("bookID"), rs.getString("b_name"), rs.getString("author"), rs.getString("category"), rs.getString("publishDate"), rs.getString("publisher"), rs.getInt("quantity"));
            return found;
        }
        
        return null;
    }
    
    
    /**
     * Add a new Book to database.
     * 
     * @param newBook = an object contain information of the book need to add.
     * 
     * @throws SQLException 
     */
    public static void addNewBook(Book newBook) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call addBook(?, ?, ?, ?, ?, ?, ?) }");
        
        finder.setString(1, newBook.getId());
        finder.setString(2, newBook.getName());
        finder.setString(3, newBook.getAuthor());
        finder.setString(4, newBook.getPublishDate());
        finder.setString(5, newBook.getPublisher());
        finder.setString(6, newBook.getCategory());
        finder.setInt(7, newBook.getQuantity());
        
        finder.executeQuery();
        
    }
    
    /**
     * Delete a book if it's contained in database.
     * 
     * @param bookID = id of the book want to delete.
     * 
     * @throws SQLException 
     */
    public static void deleteBook(String bookID) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call deleteBook(?) }");
        
        finder.setString(1, bookID);
        
        finder.executeQuery();
        
    }
}
