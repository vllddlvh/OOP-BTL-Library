package controller;


import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.CallableStatement;

import java.util.ArrayList;


import model.Document;
import model.Book;
import model.Thesis;

public class DBDocument extends DatabaseConnector {
    
    /**
     * Get Document only if you know exactly its Thesis ID / Book ISBN.
     * 
     * @param documentID = Thesis ID / Book ISBN.
     * 
     * @return Document object.
     * 
     * @throws SQLException 
     */
    public static Document getDocumentInfo(String documentID) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call searchDocument(?) }");
        
        finder.setString(1, documentID);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while (rs.next()) {
            switch(rs.getString("genre")) {
                case "Book" -> {
                    String[] category = {rs.getString(7)};
                    
                    return new Book( rs.getString(1), 
                                    rs.getString(2), 
                                   rs.getString(4), 
                                 rs.getString(5), 
                                rs.getInt(6), 
                                         category,
                             rs.getInt(3));
                } 
                case "Thesis" -> {
                    return new Thesis(rs.getString(1), 
                                    rs.getString(2), 
                                  rs.getString(4), 
                                   rs.getString(5), 
                               rs.getString(6), 
                                rs.getString(7),
                             rs.getInt(3));
                }
                default -> {
                    return null;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Search Book with given keyword types below.
     * 
     * @param titleKeyword = searching by %title%.
     * @param authorKeyword = searching by %author%.
     * @param releaseYear = search by releaseYear. Sorry, not search on range of Years.
     * @param category = on format of ENUM Book.BookCategory.
     * 
     * @return List of books found.
     * @throws SQLException 
     */
    public static ArrayList<Book> searchBook(String titleKeyword, String authorKeyword, int releaseYear, ArrayList<Book.BookCategory> category) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call searchBook(?, ?, ?, ?) }");
        
        finder.setString(1, titleKeyword);
        finder.setString(2, authorKeyword);
        finder.setInt(3, releaseYear);
        finder.setString(4, category.get(0).toString());
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        ArrayList<Book> found = new ArrayList<>();
        while (rs.next()) {
            String[] cate = {rs.getString(7)};
            found.add(new Book( rs.getString(1), 
                               rs.getString(2), 
                              rs.getString(4), 
                            rs.getString(5), 
                           rs.getInt(6), 
                             cate,
                        rs.getInt(3)));
        }
        
        return found;
    }
    
    /**
     * Search Thesis with given keyword types below.
     * 
     * @param titleKeyword = searching by %title%.
     * @param fieldOfStudyKeyword = searching by %fieldOfStudy%.
     * @param writerID = search Thesis write by a Member.
     * @param advisor = search Thesis be supervise by someone.
     * 
     * @return List of thesis found.
     * @throws SQLException 
     */
    public static ArrayList<Thesis> searchThesis(String titleKeyword, String fieldOfStudyKeyword, String writerID, String advisor) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call searchThesis(?, ?, ?, ?) }");
        
        finder.setString(1, titleKeyword);
        finder.setString(2, fieldOfStudyKeyword);
        finder.setString(3, writerID);
        finder.setString(4, advisor);
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        ArrayList<Thesis> found = new ArrayList<>();
        while (rs.next()) {
            found.add(new Thesis(rs.getString(1), 
                               rs.getString(2), 
                             rs.getString(4), 
                              rs.getString(5), 
                          rs.getString(6), 
                           rs.getString(7),
                        rs.getInt(3)));
        }
        
        return found;
    }
    
    
    /**
     * Delete a document determine by its ID.
     * 
     * @param documentID = ID of the document want to be deleted.
     * 
     * @throws SQLException 
     */
    public static void deleteDocument(String documentID) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call deleteDocument(?) }");
        
        finder.setString(1, documentID);
        
        finder.executeQuery();
    }
    
    /**
     * Add a new Book to database. 
     * 
     * @param newBook = only Book object have been full construct accept.
     * 
     * @return false if duplicate documentID.
     * 
     * @throws SQLException 
     */
    public static boolean addBook(Book newBook) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call addBook(?, ?, ?, ?, ?, ?, ?) }");
        
        finder.setString(1, newBook.getID());
        finder.setString(2, newBook.getTitle());
        finder.setString(3, newBook.getAuthor());
        finder.setInt(4, newBook.getAvailableCopies());
        finder.setString(5, newBook.getCategory().get(0).toString());
        finder.setString(6, newBook.getPublisher());
        finder.setInt(7, newBook.getReleaseYear());
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while(rs.next()) {
            return rs.getBoolean(1);
        }
        
        return false;
    } 
    
    /**
     * Add a new Thesis to database. 
     * 
     * @param newThesis = only Thesis object have been full construct accept.
     * 
     * @return false if duplicate documentID.
     * 
     * @throws SQLException 
     */
    public static boolean addThesis(Thesis newThesis) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call addThesis(?, ?, ?, ?, ?, ?, ?) }");
        
        finder.setString(1, newThesis.getID());
        finder.setString(2, newThesis.getTitle());
        finder.setString(3, newThesis.getWriterID());
        finder.setString(4, newThesis.getAdvisor());
        finder.setString(5, newThesis.getFieldOfStudy());
        finder.setString(6, newThesis.getDescription());
        finder.setInt(7, newThesis.getAvailableCopies());
        
        ResultSet rs;
        rs = finder.executeQuery();
        
        while(rs.next()) {
            return rs.getBoolean(1);
        }
        
        return false;
    } 
     
    
    /**
     * Add a new Document to database. 
     * 
     * @param newDocument = only Book or Thesis object have been full construct accept.
     * 
     * @return false if duplicate documentID.
     * 
     * @throws SQLException 
     */
    public static boolean addDocument(Document newDocument) throws SQLException {
        if (newDocument instanceof Book) {
           return addBook((Book) newDocument);
        } else if (newDocument instanceof Thesis) {
           return addThesis((Thesis) newDocument);
        }
        return false;
    }
    
    
    /**
     * Only for edit quantity of an already exists Document in library.
     * 
     * @param documentID = ID of the document.
     * @param howManyMoreCopies = change of Quantity. Negative number to decrease the totalQuantity
     * @param IDstaffWhoAdd = a mark of who do this.
     * @throws SQLException 
     */
    public static void loadMoreCopies(String documentID, int howManyMoreCopies ,String IDstaffWhoAdd) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call addThesis(?, ?, ?) }");
        
        finder.setString(1, documentID);
        finder.setInt(2, howManyMoreCopies);
        finder.setString(3, IDstaffWhoAdd);
        
        finder.executeQuery();
    }
}
