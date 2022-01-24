package spring;

public class ChangePasswordService {
  private MemberDao memberDao;

  public void changePassword(String email, String oldPassword, String newPassword) {
    Member member = memberDao.selectByEmail(email);
    if (member == null) {
      throw new MemberNotFoundException();
    }
    member.changePassword(oldPassword, newPassword);
    memberDao.update(member);
  }

  // 왜 이건 생성자로 받지 않는가? => 각자 장점이 있다. 상황에 따라 두 방식을 혼용하자.
  /*
   * 생성자 DI 방식: 빈 객체를 생성하는 시점에 모든 의존 객체가 주입된다. - 생성자의 파라미터 개수가 많을 경우, 각 인자가 어떤 의존 객체를 설정하는지 알아내려면
   * 생성자의 코드를 확인해야 한다. + 빈 객체를 생성하는 시점에 필요한 모든 의존 객체를 주입받기 때문에, 객체를 사용할 때 완전한 상태로 사용할 수 있다. 세터 메서드
   * 방식: 세터 메서드 이름을 통해 어떤 의존 객체가 주입되는지 알 수 있다. + 메서드 이름만으로 어떤 의존 객체를 설정하는지 쉽게 유추할 수 있다. - 세터 메서드를
   * 사용해서 필요한 의존 객체를 전달하지 않아도 빈 객체가 생성되기 때문에, 객체를 사용하는 시점에 NullPointerException 이 발생할 수 있다.
   */
  public void setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
  }
}
