import model.DatabaseConnector;
import model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import model.dao.UserDAO;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private Connection connection;
    private CallableStatement callableStatement;
    private ResultSet resultSet;

    @Test
    public void testLoginCorrectPassword() throws SQLException {
        // Arrange
        String userID = "user123";
        String password = "password123";

        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(1)).thenReturn("Staff");
        when(resultSet.getBoolean(2)).thenReturn(false);

        // Act
        User.LoginAlert result = UserDAO.login(userID, password);

        // Assert
        assertEquals(User.LoginAlert.CORRECT_PASSWORD_AS_STAFF, result);
    }

    @Test
    public void testLoginWrongPassword() throws SQLException {
        // Arrange
        String userID = "user123";
        String password = "wrongPassword";

        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Act
        User.LoginAlert result = UserDAO.login(userID, password);

        // Assert
        assertEquals(User.LoginAlert.WRONG_PASSWORD, result);
    }

    @Test
    public void testChangePasswordSuccess() throws SQLException {
        // Arrange
        String userID = "user123";
        String oldPassword = "oldPass";
        String newPassword = "newPass";

        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        // Act
        boolean result = UserDAO.changePassword(userID, oldPassword, newPassword);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testChangePasswordFailure() throws SQLException {
        // Arrange
        String userID = "user123";
        String oldPassword = "oldPass";
        String newPassword = "newPass";

        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(0);

        // Act
        boolean result = UserDAO.changePassword(userID, oldPassword, newPassword);

        // Assert
        assertFalse(result);
    }

    // More tests can be added for other methods
}