package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Member;


public class MemberDAOImpl implements MemberDAO {

    @Override
    public List<Member> getList() {
        try {
        Connection cons = (Connection) DatabaseConnector.getJDBCConnection();
        String sql = "SELECT * FROM library_2nd_edition.member";
        List<Member> list = new ArrayList<>();
        PreparedStatement ps = cons.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Member member = new Member();
            member.setID(rs.getString("ID"));
            member.setFirstName(rs.getString("firstName"));
            member.setLastName(rs.getString("lastName"));
            member.setContact(rs.getString("contact"));
            member.setDateOfBirth(rs.getString("dateOfBirth"));
            
            list.add(member);
        }
        ps.close();
        rs.close();
        cons.close();
        return list;
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
        return null;
    }
    
    public static void main(String[] args) {
        MemberDAO memberDAO = new MemberDAOImpl();
        System.out.println(memberDAO.getList());
        
    }
}
