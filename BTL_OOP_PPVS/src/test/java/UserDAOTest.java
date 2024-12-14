package test;

import model.dao.UserDAO;
import model.entity.User;
import model.entity.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    @BeforeAll
    public static void setup() {
        // Thiết lập kết nối cơ sở dữ liệu hoặc cơ sở dữ liệu giả
        // Cần phải có một cơ sở dữ liệu test hoặc thiết lập trước
        // Example: DatabaseConnector.connectForTesting();
    }

    @Test
    public void testLogin_ValidUser() throws SQLException {
        String userID = "testUser";
        String password = "testPassword";

        User.LoginAlert alert = UserDAO.login(userID, password);

        assertEquals(User.LoginAlert.CORRECT_PASSWORD_AS_MEMBER, alert, "Login should succeed for valid user");
    }

    @Test
    public void testLogin_InvalidPassword() throws SQLException {
        String userID = "testUser";
        String password = "wrongPassword";

        User.LoginAlert alert = UserDAO.login(userID, password);

        assertEquals(User.LoginAlert.WRONG_PASSWORD, alert, "Login should fail with wrong password");
    }

    @Test
    public void testChangePassword_Valid() throws SQLException {
        String userID = "testUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        boolean result = UserDAO.changePassword(userID, oldPassword, newPassword);

        assertTrue(result, "Password should be changed successfully");
    }

    @Test
    public void testChangePassword_Invalid() throws SQLException {
        String userID = "testUser";
        String oldPassword = "wrongOldPassword";
        String newPassword = "newPassword";

        boolean result = UserDAO.changePassword(userID, oldPassword, newPassword);

        assertFalse(result, "Password change should fail with incorrect old password");
    }

    @Test
    public void testGetFullBookPDF() throws SQLException, IOException {
        String bookID = "testBookID";

        File file = UserDAO.getFullBookPDF(bookID);

        assertNotNull(file, "File should not be null");
        assertTrue(file.exists(), "Downloaded file should exist");
    }

    @Test
    public void testGetAllMember() throws SQLException {
        var members = UserDAO.getAllMember();

        assertNotNull(members, "List of members should not be null");
        assertTrue(members.size() > 0, "There should be at least one member in the list");
    }

    @Test
    public void testGetMemberInfo_Valid() throws SQLException {
        String memberID = "testMemberID";

        Member member = UserDAO.getMemberInfo(memberID);

        assertNotNull(member, "Member should not be null");
        assertEquals(memberID, member.getID(), "Member ID should match");
    }

    @Test
    public void testGetMemberInfo_Invalid() throws SQLException {
        String memberID = "nonExistentID";

        Member member = UserDAO.getMemberInfo(memberID);

        assertNull(member, "Member should be null for non-existent ID");
    }
}
