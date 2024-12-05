import model.dao.StaffDAO;
import model.entity.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.sql.*;
import model.DatabaseConnector;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StaffDAOTest {

    @Mock
    private DatabaseConnector mockDatabaseConnector;

    @Mock
    private CallableStatement mockCallableStatement;

    @Mock
    private ResultSet mockResultSet;

    private StaffDAO staffDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        staffDAO = new StaffDAO();
    }

    // Test the getStaffInfo method
    @Test
    public void testGetStaffInfo() throws SQLException {
        String staffID = "S123";
        
        // Mock database connection and statement
        when(mockDatabaseConnector.getConnection()).thenReturn(mockConnection());
        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet());
        
        // Mock the result set
        when(mockResultSet.getString(1)).thenReturn("S123");
        when(mockResultSet.getString(2)).thenReturn("John");
        when(mockResultSet.getString(3)).thenReturn("Doe");
        when(mockResultSet.getString(4)).thenReturn("123456789");
        when(mockResultSet.getString(5)).thenReturn("Manager");
        when(mockResultSet.getString(6)).thenReturn("Sales");

        Staff staff = staffDAO.getStaffInfo(staffID);
        
        // Assert the values returned
        assertNotNull(staff);
        assertEquals("S123", staff.getID());
        assertEquals("John", staff.getFirstName());
        assertEquals("Doe", staff.getLastName());
    }

    // Test for case where no staff is found
    @Test
    public void testGetStaffInfoNotFound() throws SQLException {
        String staffID = "S123";

        // Mock database connection and statement
        when(mockDatabaseConnector.getConnection()).thenReturn(mockConnection());
        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet());

        // Simulate an empty result set
        when(mockResultSet.next()).thenReturn(false);

        Staff staff = staffDAO.getStaffInfo(staffID);

        // Assert that no staff is found
        assertNull(staff);
    }

    // Test the addNewStaff method
    @Test
    public void testAddNewStaff() throws SQLException {
        Staff newStaff = new Staff("S124", "Alice", "Smith", "987654321", "Developer", "S123");
        String reportTo = "S123";

        // Mock database connection and callable statement
        when(mockDatabaseConnector.getConnection()).thenReturn(mockConnection());
        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet());

        // Simulate a successful insertion (returning true)
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBoolean(1)).thenReturn(true);

        boolean result = staffDAO.addNewStaff(newStaff, reportTo);

        // Assert that the staff was added successfully
        assertTrue(result);
    }

    // Test for case where the staff ID already exists (duplicate entry)
    @Test
    public void testAddNewStaffDuplicate() throws SQLException {
        Staff newStaff = new Staff("S124", "Alice", "Smith", "987654321", "Developer", "S123");
        String reportTo = "S123";

        // Mock database connection and callable statement
        when(mockDatabaseConnector.getConnection()).thenReturn(mockConnection());
        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet());

        // Simulate a failed insertion due to duplicate user ID
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBoolean(1)).thenReturn(false);

        boolean result = staffDAO.addNewStaff(newStaff, reportTo);

        // Assert that the staff was not added due to duplicate user ID
        assertFalse(result);
    }

    // Mock method to simulate database connection
    private Connection mockConnection() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareCall(anyString())).thenReturn(mockCallableStatement);
        return mockConnection;
    }

    // Mock method to simulate result set
    private ResultSet mockResultSet() throws SQLException {
        return mock(ResultSet.class);
    }
}