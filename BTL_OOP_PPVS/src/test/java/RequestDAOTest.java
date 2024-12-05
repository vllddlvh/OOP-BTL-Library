import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.*;
import model.dao.RequestDAO;
import model.entity.Request;

class RequestDAOTest {
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private CallableStatement callableStatement;
    private ResultSet resultSet;
    
    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        callableStatement = mock(CallableStatement.class);
        resultSet = mock(ResultSet.class);
        
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareCall(anyString())).thenReturn(callableStatement);
    }
    
    @Test
    void testGetAllRequest() throws SQLException {
        // Setup mock result set
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString(1)).thenReturn("requestID");
        when(resultSet.getString(2)).thenReturn("userID");
        when(resultSet.getString(3)).thenReturn("documentID");
        when(resultSet.getInt(4)).thenReturn(1);
        when(resultSet.getString(5)).thenReturn("borrowDate");
        when(resultSet.getString(6)).thenReturn("returnDate");
        when(resultSet.getString(7)).thenReturn("user_fullName");
        when(resultSet.getString(8)).thenReturn("document_title");

        // Call the method to test
        LinkedList<Request> requests = RequestDAO.getAllRequest();

        // Assertions
        assertNotNull(requests);
        assertEquals(1, requests.size());
        assertEquals("requestID", requests.get(0).getRequestID());
    }
    
    @Test
    void testBorrowDocument() throws SQLException {
        // Setup mock result set
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean("Result")).thenReturn(true);

        // Call the method to test
        boolean result = RequestDAO.borrowDocument("userID", "documentID");

        // Assertions
        assertTrue(result);
    }

    @Test
    void testUpdateDateRequest() throws SQLException {
        // Setup mock prepared statement
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Create a sample Request object
        Request request = new Request("requestID", "userID", "documentID", 1, "2024-01-01", "2024-01-15");

        // Call the method to test
        boolean result = RequestDAO.updateDateRequest(request);

        // Assertions
        assertTrue(result);
    }

    @Test
    void testReturnDocument() throws SQLException {
        // Setup mock result set
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean("Result")).thenReturn(true);

        // Call the method to test
        boolean result = RequestDAO.returnDocument("userID", "requestID");

        // Assertions
        assertTrue(result);
    }

    @Test
    void testGetUnreturnDocument() throws SQLException {
        // Setup mock callable statement and result set
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString(1)).thenReturn("requestID");
        when(resultSet.getString(2)).thenReturn("documentID");
        when(resultSet.getInt(3)).thenReturn(1);
        when(resultSet.getString(4)).thenReturn("borrowDate");

        // Call the method to test
        ArrayList<Request> requests = RequestDAO.getUnreturnDocument("userID");

        // Assertions
        assertNotNull(requests);
        assertEquals(1, requests.size());
        assertEquals("requestID", requests.get(0).getRequestID());
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Close resources
        if (preparedStatement != null) preparedStatement.close();
        if (callableStatement != null) callableStatement.close();
        if (resultSet != null) resultSet.close();
        if (connection != null) connection.close();
    }
}