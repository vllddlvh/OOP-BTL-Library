package model.dao.test;

import model.dao.UserDAO;
import model.entity.User;
import model.entity.Member;
import model.dao.MemberDAO;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {

    @BeforeAll 
    public static void setup() throws SQLException {
        // Thiết lập kết nối cơ sở dữ liệu hoặc cơ sở dữ liệu giả
        // Cần phải có một cơ sở dữ liệu test hoặc thiết lập trước
        // Example: DatabaseConnector.connectForTesting();
        
        if (MemberDAO.addNewMember(new Member("testUser",
                                          "testUserFirstName",
                                           "testUserLastName",
                                           "testUserContact",
                                           "2024-12-15"                               
        ))) {
            System.out.println("Setup done");
        } else {
            System.out.println("Setup FAILED");
        }
    }
    
    @AfterAll 
    public static void clear() throws SQLException {
        UserDAO.deleteUser("testUser");
        System.out.println("clear");
    }

    @Test   
    @Order(3)
    public void testLogin_ValidUser() throws SQLException {
        String userID = "testUser";
        String password = "truePassword";

        User.LoginAlert alert = UserDAO.login(userID, password);

        System.out.println("3. testLogin_ValidUser: running...");
        assertEquals(User.LoginAlert.CORRECT_PASSWORD_AS_MEMBER, alert, "Login should succeed for valid user");
    }

    @Test   
    @Order(4)
    public void testLogin_InvalidPassword() throws SQLException {
        String userID = "testUser";
        String password = "wrongPassword";

        User.LoginAlert alert = UserDAO.login(userID, password);

        System.out.println("4. testLogin_InvalidUser: running...");
        assertEquals(User.LoginAlert.WRONG_PASSWORD, alert, "Login should fail with wrong password");
    }

    @Test   
    @Order(1)
    public void testChangePassword_Valid() throws SQLException {
        String userID = "testUser";
        String oldPassword = "testUser";
        String newPassword = "truePassword";

        boolean result = UserDAO.changePassword(userID, oldPassword, newPassword);

        System.out.println("1. testChangePassword_Valid: running...");
        assertTrue(result, "Password should be changed successfully");
    }

    @Test   
    @Order(2)
    public void testChangePassword_Invalid() throws SQLException {
        String userID = "testUser";
        String oldPassword = "wrongPassword";
        String newPassword = "newPassword";

        boolean result = UserDAO.changePassword(userID, oldPassword, newPassword);
        
        System.out.println("2. testChangePassword_Invalid: running...");
        assertFalse(result, "Password change should fail with incorrect old password");
    }

    @Test   
    @Order(5)
    public void testGetMemberInfo_Valid() throws SQLException {
        String memberID = "testUser";

        User member = UserDAO.getMemberInfo(memberID);

        System.out.println("5. testGetMemberInfo_Valid: running...");
        assertNotNull(member, "Member should not be null");
        assertEquals(memberID, member.getID(), "Member ID should match");
    }

    @Test   
    @Order(6)
    public void testGetMemberInfo_Invalid() throws SQLException {
        String memberID = "nonExist";

        Member member = UserDAO.getMemberInfo(memberID);

        System.out.println("6. testGetMemberInfo_Invalid: running...");
        assertNull(member, "Member should be null for non-existent ID");
    }
}
