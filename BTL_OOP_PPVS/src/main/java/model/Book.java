package model;

import java.util.ArrayList;

/**
 *
 * @author Littl
 */
public class Book extends Document {
    public enum BookCategory {
        FICTION("Fiction"),
        NON_FICTION("Non-fiction");
        
        
        private final String convertString;
        private BookCategory(String convertValue) {
            convertString = convertValue;
        }
        
        @Override
        public String toString() {
            return this.convertString;
        }
    }
    
    protected String author;
    protected String publisher;
    protected int releaseYear;
    protected ArrayList<BookCategory> category;
    
    public Book (String ISBN, String title, String author, String publisher, int releaseYear, String[] category, int availableCopies) {
        this.ID = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.availableCopies = availableCopies;
        setCategory(category);
        
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

    public ArrayList<BookCategory> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<BookCategory> category) {
        this.category = category;
    }
    
    public void setCategory(String[] category) {
        for (String x : category) {
            // Quy tắc thông nhất là chỉ 1 chữ cái đầu viết hoa
            switch (x) {
                case "Fiction": 
                    this.category.add(BookCategory.FICTION);
                    break;
                case "Non-fiction":
                    this.category.add(BookCategory.NON_FICTION);
                    break;
                default:
            }
        }
    }
}
