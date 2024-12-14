import org.junit.jupiter.api.*;
import java.sql.SQLException;
import model.DatabaseConnector;
import model.dao.StaffDAO;
import model.entity.Staff;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StaffDAOTest {

    // This ID should exist in the test database.
    private static final String EXISTING_STAFF_ID = "S001";
    // This ID should not exist in the test database.
    private static final String NON_EXISTING_STAFF_ID = "S999";
    // Details for a new staff member to add.
    private static final String NEW_STAFF_ID = "S002";
    private static final String REPORT_TO_ID = "U001";

    @Test
    @Order(1)
    public void testGetStaffInfo_ExistingStaff() throws SQLException {
        Staff staff = StaffDAO.getStaffInfo(EXISTING_STAFF_ID);
        assertNotNull(staff, "Staff should be retrieved.");
        assertEquals(EXISTING_STAFF_ID, staff.getID(), "Staff ID should match.");
    }

    @Test
    @Order(2)
    public void testGetStaffInfo_NonExistingStaff() throws SQLException {
        Staff staff = StaffDAO.getStaffInfo(NON_EXISTING_STAFF_ID);
        assertNull(staff, "Staff should not exist.");
    }

    @Test
    @Order(3)
    public void testAddNewStaff_Success() throws SQLException {
        Staff newStaff = new Staff(NEW_STAFF_ID, "John", "Doe", "john.doe@staff.ppvs", "Assistant Librarian", REPORT_TO_ID);
        boolean isAdded = StaffDAO.addNewStaff(newStaff, REPORT_TO_ID);
        assertTrue(isAdded, "New staff should be added successfully.");

        // Verify the staff was added.
        Staff addedStaff = StaffDAO.getStaffInfo(NEW_STAFF_ID);
        assertNotNull(addedStaff, "Newly added staff should exist.");
        assertEquals("John", addedStaff.getFirstName(), "First name should match.");
        assertEquals("Doe", addedStaff.getLastName(), "Last name should match.");
    }

    @Test
    @Order(4)
    public void testAddNewStaff_DuplicateID() throws SQLException {
        Staff duplicateStaff = new Staff(EXISTING_STAFF_ID, "Jane", "Smith", "jane.smith@staff.ppvs", "Librarian", REPORT_TO_ID);
        boolean isAdded = StaffDAO.addNewStaff(duplicateStaff, REPORT_TO_ID);
        assertFalse(isAdded, "Adding staff with duplicate ID should fail.");
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        // Cleanup: Remove any test data created during tests.
        StaffDAO.getStaffInfo(NEW_STAFF_ID);
        DatabaseConnector.getConnection().createStatement().execute("DELETE FROM Staff WHERE ID = '" + NEW_STAFF_ID + "'");
    }
}
