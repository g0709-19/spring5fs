package spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {
  private static long nextId = 0;
  private Map<String, Member> map = new HashMap<>();

  public Member selectByEmail(String email) {
    return map.get(email); // 레퍼런스를 리턴해주지 않나? 왜 update 를 호출해야 하지?
  }

  public void insert(Member member) {
    member.setId(++nextId);
    map.put(member.getEmail(), member);
  }

  public void update(Member member) {
    map.put(member.getEmail(), member);
  }

  public Collection<Member> selectAll() {
    return map.values();
  }
}
