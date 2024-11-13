package model;

/**
 *
 * @author Littl
 */
public class Member extends User {
    protected String contact = "example@member.ppvs";
    protected String dateOfBirth = "2005-01-01";

    public Member(String ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * default Full-information Constructor for Member.
     * 
     * @param ID = memberID (MSV)
     * @param firstName = first name
     * @param lastName = last name
     * @param contact = email/ phone/...
     * @param dateOfBirth = format "YYYY-MM-DD"
     */
    public Member(String ID, String firstName, String lastName, String contact, String dateOfBirth) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.dateOfBirth = dateOfBirth;
        
    }
    
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    
}
