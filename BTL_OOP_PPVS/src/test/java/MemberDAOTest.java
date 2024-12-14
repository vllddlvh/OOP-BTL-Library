import model.dao.MemberDAO;
import model.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberDAOTest {

    @BeforeEach
    void setUp() {
        // Thiết lập cơ sở dữ liệu cho kiểm thử.
        // Có thể chèn các dữ liệu thử nghiệm vào cơ sở dữ liệu để kiểm tra.
    }

    @Test
    void testGetAllMember() throws SQLException {
        // Kiểm tra lấy tất cả các thành viên từ cơ sở dữ liệu
        List<Member> members = MemberDAO.getAllMember();
        assertNotNull(members, "Danh sách thành viên không được null");
        assertTrue(members.size() > 0, "Danh sách thành viên không được rỗng");
    }

    @Test
    void testGetMemberInfo() throws SQLException {
        // Kiểm tra lấy thông tin thành viên theo ID
        String testID = "1662"; // Thay bằng ID người dùng thực tế có trong cơ sở dữ liệu
        Member member = MemberDAO.getMemberInfo(testID);
        
        assertNotNull(member, "Thành viên không được null");
        assertEquals(testID, member.getID(), "ID thành viên không khớp");
        assertEquals("Nguyễn Minh", member.getFirstName(), "Tên không khớp");
    }

    @Test
void testAddNewMember() throws SQLException {
    // Thử thêm một thành viên mới
    Member newMember = new Member("677", "Janee", "Doee", "jane.doe@example.com", "1990-05-15");
    
    // Thêm thành viên vào cơ sở dữ liệu
    boolean isAdded = MemberDAO.addNewMember(newMember);
    
    assertTrue(isAdded, "Thành viên mới không thể thêm vào cơ sở dữ liệu");
    
    // Kiểm tra lại trong cơ sở dữ liệu
    Member addedMember = MemberDAO.getMemberInfo("677");
    assertNotNull(addedMember, "Thành viên không được thêm vào");
    assertEquals("Janee", addedMember.getFirstName(), "Tên không khớp");
    
    // Sau khi kiểm tra xong, xóa thành viên đã thêm vào
    boolean isDeleted = MemberDAO.deleteMember(addedMember);
    
}


    @Test
    void testUpdateMember() throws SQLException {
        // Thử cập nhật thông tin thành viên
        Member existingMember = new Member("12345");
        existingMember.setID("123");
        existingMember.setFirstName("UpdatedFirstName");
        existingMember.setLastName("UpdatedLastName");
        existingMember.setContact("updated.contact@example.com");
        existingMember.setDateOfBirth("1985-06-20");
        
        boolean isUpdated = MemberDAO.updateMember(existingMember);
        
        assertTrue(isUpdated, "Không thể cập nhật thành viên");
        
        // Kiểm tra lại thông tin đã được cập nhật
        Member updatedMember = MemberDAO.getMemberInfo("12345");
        assertNotNull(updatedMember, "Không tìm thấy thành viên sau khi cập nhật");
        assertEquals("UpdatedFirstName", updatedMember.getFirstName(), "Tên không khớp");
    }

    @Test
    void testDeleteMember() throws SQLException {
        // Thử xóa một thành viên
        Member memberToDelete = new Member("67890"); // Thay bằng ID thành viên thực tế
        boolean isDeleted = MemberDAO.deleteMember(memberToDelete);
        
        assertTrue(isDeleted, "Không thể xóa thành viên");
        
        // Kiểm tra lại xem thành viên đã bị xóa chưa
        Member deletedMember = MemberDAO.getMemberInfo("67890");
        assertNull(deletedMember, "Thành viên vẫn còn tồn tại trong cơ sở dữ liệu");
    }
}
