package model;

/**
 *
 * @author Littl
 */
public abstract class User {
    public enum loginAlert {
        FIRST_TIMES_LOGIN,
        WRONG_PASSWORD,
        CORRECT_PASSWORD
    }
    
    protected String ID;
    protected String firstName;
    protected String lastName;
    
    public loginAlert Login(String userID) {
        return loginAlert.WRONG_PASSWORD;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
}
