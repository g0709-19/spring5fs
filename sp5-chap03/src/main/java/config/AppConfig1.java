package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.MemberDao;
import spring.MemberPrinter;

@Configuration
public class AppConfig1 {
  @Bean
  public MemberDao memberDao() {
    MemberDao dao = new MemberDao();
    System.out.println("memberdao1 " + dao);
    return dao;
  }

  @Bean
  public MemberPrinter memberPrinter() {
    return new MemberPrinter();
  }

  @Bean
  public MemberDao memberDao2() {
    MemberDao dao = new MemberDao();
    System.out.println("memberdao2 " + dao);
    return dao;
  }
}
