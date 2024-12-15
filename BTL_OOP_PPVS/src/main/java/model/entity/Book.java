package model.entity;

import java.sql.SQLException;
import java.io.IOException;
        
import java.util.ArrayList;
import java.util.List;
import model.dao.DocumentDAO;

/**
 *
 * @author Littl
 */
public class Book extends Document {
    
    protected String author;
    protected String publisher;
    protected int releaseYear;
    
    /**
     * Contructor dạng raw. Chỉ cho model.DAO sử dụng. Vì dạng category vẫn đang ở dạng mã hóa.
     * 
     * @param ISBN = new Book ID/ ISBN code.
     * @param title = its title.
     * @param availableCopies = load copies amount.
     * @param author = author name.
     * @param publisher = publisher name.
     * @param releaseYear = release in what year (after 1900)
     * @param description = the book introduction.
     * @param category = category ENCRYPTED FORM.
     * @param language = language type
     * 
     * @throws IOException 
     */
    public Book(String ISBN, String title, int availableCopies, 
            String author, String publisher, int releaseYear, 
            String description, int category, String language) throws IOException {
        
        this.ID = ISBN;
        this.title = title;
        this.availableCopies = availableCopies;
        this.author = author;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.description = description;
        this.category = CategoryType.decode(category);
        if (language != null) {
            this.language = language;
        }
    }
    
    /**
     * Constructor dạng phổ thông. Có thể đa dụng ở package khác.
     * 
     * @param ISBN = new Book ID/ ISBN code.
     * @param title = its title.
     * @param availableCopies = load copies amount.
     * @param author = author name.
     * @param publisher = publisher name.
     * @param releaseYear = release in what year (after 1900)
     * @param description = the book introduction.
     * @param category = category AFTER DECODED FORM, as a list
     * @param language = language type
     */
    public Book(String ISBN, String title, int availableCopies, 
            String author, String publisher, int releaseYear, 
            String description, ArrayList<String> category, String language) {
        
        this.ID = ISBN;
        this.title = title;
        this.availableCopies = availableCopies;
        this.author = author;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.description = description;
        this.category = category;
        if (language != null) {
            this.language = language;
        }
    }
    
    /**
     * Constructor dạng thô. GetText trực tiếp từ view.
     * Category sẽ cần cắt nhở sang dạng list để mã hóa sau này.
     * 
     * @param ISBN = new Book ID/ ISBN code.
     * @param title = its title.
     * @param availableCopies = load copies amount.
     * @param author = author name.
     * @param publisher = publisher name.
     * @param releaseYear = release in what year (after 1900)
     * @param description = the book introduction.
     * @param category = category AFTER DECODED FORM as a long String
     * @param language = language type
     */
    public Book(String ISBN, String title, int availableCopies, String author, String publisher, int releaseYear, String description, String category, String language) {
        this.ID = ISBN;
        this.title = title;
        this.availableCopies = availableCopies;
        this.author = author;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.description = description;
        this.category = new ArrayList<>();
        if (language != null) {
            this.language = language;
        }
        String[] arr = category.split(",|[|]");
        for (String x : arr) {
            this.category.add(x.trim());
        }
    }
    
    /**
     * Lấy thông tin về một quyển sách từ database thông qua ID tài liệu.
     * 
     * @param documentID = mã sách.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    public Book(String documentID) throws SQLException, IOException {
        Document docu = getDocumentInfo(documentID);
        
        if (docu != null && docu instanceof Book) {
            Book getter = (Book) docu;
            
            this.ID = getter.ID;
            this.title = getter.title;
            this.author = getter.author;
            this.publisher = getter.publisher;
            this.releaseYear = getter.releaseYear;
            this.availableCopies = getter.availableCopies;
            this.category = new ArrayList<>();
            this.category.addAll(getter.category);
            this.language = getter.language;
        }
    }
    
    /**
     * Một clone cho sách hiện có. 
     * Cần thiết trong sửa đổi/reset thông tin tài liệu.
     * 
     * Có vẻ là Prototype Pattern thô sơ.
     * 
     * @param org = mẫu gốc
     */
    public Book(Book org) {
        this.ID = org.ID;
        this.title = org.title;
        this.author = org.author;
        this.publisher = org.publisher;
        this.releaseYear = org.releaseYear;
        this.description = org.description;
        this.availableCopies = org.availableCopies;
        this.language = org.language;
        
        this.category = new ArrayList<>();
        this.category.addAll(org.category);
        
        this.PDF = org.PDF;
        
        this.cover = org.cover;
        this.haveCover = (org.cover != null);      // để không bị dán đè ảnh khi nạp PDF
        this.coverFormat = org.coverFormat;
    }
    
    /**
     * Search Book with given keyword types below.
     * 
     * @param titleKeyword = searching by %title%.
     * @param authorKeyword = searching by %author%.
     * @param releaseYear = search by releaseYear. Sorry, not search on range of Years.
     * @param category = on format of ENUM Book.BookCategory.
     * @param language
     * 
     * @return List of books found.
     * 
     * @throws SQLException 
     * @throws java.io.IOException 
     */
    public static ArrayList<Book> searchBook(String titleKeyword, String authorKeyword, int releaseYear, 
                                                                    List<String> category, String language) throws SQLException, IOException {
        return DocumentDAO.searchBook(titleKeyword, authorKeyword, releaseYear, CategoryType.encrypt(category), language);
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    
}
