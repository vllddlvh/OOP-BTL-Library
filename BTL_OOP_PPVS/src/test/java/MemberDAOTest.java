import model.entity.Member;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import model.dao.MemberDAO;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberDAOTest {

    private Member testMember;

    @BeforeAll
    public void setup() {
        // Tạo một Member test mẫu để dùng trong các bài kiểm tra
        testMember = new Member("testID", "Test", "User", "123456789", "2000-01-01");
    }

    @Test
    @Order(1)
    public void testAddNewMember() throws SQLException {
        boolean result = MemberDAO.addNewMember(testMember);
        assertTrue(result, "Adding a new member should return true");
    }

    @Test
    @Order(2)
    public void testGetAllMember() throws SQLException {
        ArrayList<Member> members = MemberDAO.getAllMember();
        assertNotNull(members, "Member list should not be null");
        assertTrue(members.size() > 0, "Member list should have at least one member");
    }

    @Test
    @Order(3)
    public void testGetMemberInfo() throws SQLException {
        Member member = MemberDAO.getMemberInfo("testID");
        assertNotNull(member, "Member info should not be null");
        assertEquals(testMember.getID(), member.getID(), "Member ID should match the test member");
    }

    @Test
    @Order(4)
    public void testUpdateMember() throws SQLException {
        // Cập nhật thông tin của testMember
        testMember.setFirstName("UpdatedName");
        boolean result = MemberDAO.updateMember(testMember);
        assertTrue(result, "Updating member should return true");

        // Kiểm tra thông tin đã cập nhật
        Member updatedMember = MemberDAO.getMemberInfo("testID");
        assertNotNull(updatedMember, "Updated member should not be null");
        assertEquals("UpdatedName", updatedMember.getFirstName(), "Member first name should be updated");
    }

    @Test
    @Order(5)
    public void testDeleteMember() throws SQLException {
        boolean result = MemberDAO.deleteMember(testMember);
        assertTrue(result, "Deleting member should return true");

        // Kiểm tra member đã bị xóa
        Member deletedMember = MemberDAO.getMemberInfo("testID");
        assertNull(deletedMember, "Deleted member should be null");
    }

    @AfterAll
    public void cleanup() throws SQLException {
        // Đảm bảo xóa testMember trong trường hợp test bị thất bại
        MemberDAO.deleteMember(testMember);
    }
}
