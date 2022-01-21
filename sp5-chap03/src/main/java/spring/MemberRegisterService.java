package spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
    private MemberDao memberDao;
    
    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    // 이런 메서드는 SRP 를 위반하는가?
    public Long regist(RegisterRequest req) {
        Member member = memberDao.selectByEmail(req.getEmail());
        if (member != null) {
            throw new DuplicateMemberException("email duplicated" + req.getEmail());
        }
        Member newMember = new Member(req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
