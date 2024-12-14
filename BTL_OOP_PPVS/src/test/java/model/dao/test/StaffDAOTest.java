package model.dao.test;

import org.junit.jupiter.api.*;
import java.sql.SQLException;
import model.dao.StaffDAO;
import model.dao.UserDAO;
import model.entity.Staff;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StaffDAOTest {

    // This ID should exist in the test database.
    private static final String EXISTING_STAFF_ID = "23021662";
    // This ID should not exist in the test database.
    private static final String NON_EXISTING_STAFF_ID = "S999";
    // Details for a new staff member to add.

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
        Staff newStaff = new Staff(NON_EXISTING_STAFF_ID, "John", "Doe", "john.doe@staff.ppvs", "Assistant Librarian", EXISTING_STAFF_ID);
        boolean isAdded = StaffDAO.addNewStaff(newStaff, EXISTING_STAFF_ID);
        assertTrue(isAdded, "New staff should be added successfully.");

        // Verify the staff was added.
        Staff addedStaff = StaffDAO.getStaffInfo(NON_EXISTING_STAFF_ID);
        assertNotNull(addedStaff, "Newly added staff should exist.");
        assertEquals("John", addedStaff.getFirstName(), "First name should match.");
        assertEquals("Doe", addedStaff.getLastName(), "Last name should match.");
        assertEquals("Assistant Librarian", addedStaff.getJobTitle(), "Job Title should match.");
        assertEquals("john.doe@staff.ppvs", addedStaff.getContact(), "Contact should match.");
    }

    @Test
    @Order(4)
    public void testAddNewStaff_DuplicateID() throws SQLException {
        Staff duplicateStaff = new Staff(EXISTING_STAFF_ID, "Jane", "Smith", "jane.smith@staff.ppvs", "Librarian", NON_EXISTING_STAFF_ID);
        boolean isAdded = StaffDAO.addNewStaff(duplicateStaff, NON_EXISTING_STAFF_ID);
        assertFalse(isAdded, "Adding staff with duplicate ID should fail.");
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        // Cleanup: Remove any test data created during tests.
        UserDAO.deleteUser(NON_EXISTING_STAFF_ID);
    }
}
