package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @Override 
    public boolean addMember(Member s) {
        Connection cons;
        String sql = "INSERT INTO member(ID, firstName, lastName, contact, DateOfBirth) VALUES( ?, ?, ?, ?, ?)";
        try {
            cons = (Connection) DatabaseConnector.getJDBCConnection();
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, s.getID());
            ps.setString(2, s.getFirstName());
            ps.setString(3, s.getLastName());
            ps.setString(4, s.getContact());
            ps.setString(5, s.getDateOfBirth());
            
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return false;
    }
    
    public static void main(String[] args) {
        MemberDAO memberDAO = new MemberDAOImpl();
        System.out.println(memberDAO.getList());
        
    }
}
