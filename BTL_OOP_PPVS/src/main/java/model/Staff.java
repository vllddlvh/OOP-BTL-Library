package model;

/**
 *
 * @author Littl
 */
public class Staff extends User {
    protected String contact = "example@staff.ppvs";
    protected String jobTitle = "Librarian";
    protected String introducerID = "U001";

    
    public Staff(String ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Staff(String ID, String firstName, String lastName, String contact, String jobTitle, String introducerID) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.jobTitle = jobTitle;
        this.introducerID = introducerID;
    }
    
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getIntroducerID() {
        return introducerID;
    }

    public void setIntroducerID(String introducerID) {
        this.introducerID = introducerID;
    }
    
    
}
