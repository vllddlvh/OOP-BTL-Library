package service;

import controller.MemberDAO;
import controller.MemberDAOImpl;
import java.util.List;
import model.Member;

public class MemberServiceImpl implements MemberService {

    private MemberDAO memberDAO = null;
    
    public MemberServiceImpl() {
        memberDAO = new MemberDAOImpl();
    }
    
    @Override
    public List<Member> getList() {
       return memberDAO.getList();
    }
    
}
