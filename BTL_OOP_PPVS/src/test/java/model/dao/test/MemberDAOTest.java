package model.dao.test;

import model.dao.MemberDAO;
import model.entity.Member;
import java.sql.SQLException;
import java.util.List;
import model.dao.UserDAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberDAOTest {

    @BeforeAll
    static void setUp() {
        // Thiết lập cơ sở dữ liệu cho kiểm thử.
        // Có thể chèn các dữ liệu thử nghiệm vào cơ sở dữ liệu để kiểm tra.
    }

    @Test
    @Order(1)
    void testGetAllMember() throws SQLException {
        // Kiểm tra lấy tất cả các thành viên từ cơ sở dữ liệu
        List<Member> members = MemberDAO.getAllMember();
        assertNotNull(members, "Danh sách thành viên không được null");
        assertTrue(!members.isEmpty(), "Danh sách thành viên không được rỗng");
    }

    @Test
    @Order(3)       
    void testGetMemberInfo() throws SQLException {
        // Kiểm tra lấy thông tin thành viên theo ID
        String testID = "677";
        Member member = MemberDAO.getMemberInfo(testID);
        Member checkupMember = new Member("677", "Janee", "Doee", "jane.doe@example.com", "1990-05-15");
        
        assertNotNull(member, "Thành viên không được null");
        assertEquals(testID, member.getID(), "ID thành viên không khớp");
        assertEquals(checkupMember.getFirstName(), member.getFirstName(), "Tên không khớp");
        assertEquals(checkupMember.getLastName(), member.getLastName(), "Họ không khớp");
        assertEquals(checkupMember.getContact(), member.getContact(), "Thông tin liên hệ không khớp");
        assertEquals(checkupMember.getDateOfBirth(), member.getDateOfBirth(), "Ngày sinh không khớp");
    }

    @Test
    @Order(2)
    void testAddNewMember() throws SQLException {
        // Thử thêm một thành viên mới
        Member newMember = new Member("677", "Janee", "Doee", "jane.doe@example.com", "1990-05-15");
    
        // Thêm thành viên vào cơ sở dữ liệu
        boolean isAdded = MemberDAO.addNewMember(newMember);
    
        assertTrue(isAdded, "Thành viên mới không thể thêm vào cơ sở dữ liệu");
    }


    @Test
    @Order(4)
    void testUpdateMember() throws SQLException {
        // Thử cập nhật thông tin thành viên
        Member originalMember = new Member("677");
        originalMember.setFirstName("UpdatedFirstName");
        originalMember.setLastName("UpdatedLastName");
        originalMember.setContact("updated.contact@example.com");
        originalMember.setDateOfBirth("1985-06-20");
        
        boolean isUpdated = MemberDAO.updateMember(originalMember);
        
        assertTrue(isUpdated, "Không thể cập nhật thành viên");
        
        // Kiểm tra lại thông tin đã được cập nhật
        Member updatedMember = MemberDAO.getMemberInfo("677");
        assertNotNull(updatedMember, "Thành viên không được null");
        assertEquals(originalMember.getID(), updatedMember.getID(), "ID thành viên không khớp");
        assertEquals(originalMember.getFirstName(), updatedMember.getFirstName(), "Tên không khớp");
        assertEquals(originalMember.getLastName(), updatedMember.getLastName(), "Họ không khớp");
        assertEquals(originalMember.getContact(), updatedMember.getContact(), "Thông tin liên hệ không khớp");
        assertEquals(originalMember.getDateOfBirth(), updatedMember.getDateOfBirth(), "Ngày sinh không khớp");
    }

    @Test
    @Order(5)
    void testDeleteMember() throws SQLException {
        // Thử xóa một thành viên
        Member memberToDelete = new Member("677");
        UserDAO.deleteUser(memberToDelete.getID());
        
        // Kiểm tra lại xem thành viên đã bị xóa chưa
        Member deletedMember = MemberDAO.getMemberInfo("677");
        assertNull(deletedMember, "Thành viên vẫn còn tồn tại trong cơ sở dữ liệu");
    }
}
