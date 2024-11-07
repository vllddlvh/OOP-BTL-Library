package controller;


import model.User;
import model.Student;


import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public class DBUser extends DatabaseConnector {
    
    public static User getUserInfo(User in)throws SQLException {
        if (in.getRight() == User.SET_STUDENT) {
            ResultSet rs;
            Student found;
        
            CallableStatement finder = (CallableStatement) connection.prepareCall("{ call findStudent(?) }");
        
            finder.setString(1, in.getID());
        
            rs = finder.executeQuery();
        
            while (rs.next()) {
                found = new Student(in.getID(), rs.getString("userName"), rs.getString("dateOfBirth"), rs.getString("contact"), rs.getString("classID"), rs.getInt("age"));
                return found;
            }
        
            return null;
        }
        
        return null;
    }
    
    public static void changeUserName(String userID, String newUserName) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call changeUserName(?, ?) }");
        
        finder.setString(1, userID);
        finder.setString(2, newUserName);
        
        finder.executeQuery();
    }
    
    public static void changeContact(String userID, String newContact) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call changeContact(?, ?) }");
        
        finder.setString(1, userID);
        finder.setString(2, newContact);
        
        finder.executeQuery();
    }
    
    public static void addNewStudent(Student newStudent) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call addStudent(?, ?, ?, ?, ?, ?) }");
        
        finder.setString(1, newStudent.getID());
        finder.setString(2, newStudent.getUserName());
        finder.setString(3, newStudent.getDateOfBirth());
        finder.setString(4, newStudent.getContact());
        finder.setString(5, newStudent.getClassID());
        finder.setInt(6, newStudent.getAge());
                
        finder.executeQuery();
    }
    
    public static void deleteUser(String userID) throws SQLException {
        CallableStatement finder = (CallableStatement) connection.prepareCall("{ call deleteUser(?) }");
        
        finder.setString(1, userID);
                
        finder.executeQuery();
    }
}
