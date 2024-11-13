package model;

/**
 *
 * @author Littl
 */
public class Thesis extends Document {
    protected String writerID;
    protected String advisor;
    protected String fieldOfStudy;
    protected String description;

    public Thesis(String ID, String title, String writerID, String advisor, String fieldOfStudy, String description, int availableCopies) {
        this.ID = ID;
        this.title = title;
        this.availableCopies = availableCopies;
        this.writerID = writerID;
        this.advisor = advisor;
        this.fieldOfStudy = fieldOfStudy;
        this.description = description;
    }

    
    
    public String getWriterID() {
        return writerID;
    }

    public void setWriterID(String writerID) {
        this.writerID = writerID;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
