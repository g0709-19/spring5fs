package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.VersionPrinter;

@Configuration
public class AppConfig2 {
    // 해당 타입의 Bean 을 찾아서 필드에 할당한다.
    // 타입으로 찾기 때문에 필드 이름은 상관이 없다.
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberPrinter memberPrinter;
    
    @Bean
    public MemberRegisterService memberRegisterService() {
        System.out.println("register service " + memberDao);
        return new MemberRegisterService(memberDao);
    }
    
    @Bean
    public ChangePasswordService changePasswordService() {
        ChangePasswordService changePasswordService = new ChangePasswordService();
        changePasswordService.setMemberDao(memberDao);
        return changePasswordService;
    }
    
    @Bean
    public MemberListPrinter memberListPrinter() {
        return new MemberListPrinter(memberDao, memberPrinter);
    }
    
    @Bean
    public MemberInfoPrinter memberInfoPrinter() {
        MemberInfoPrinter memberInfoPrinter = new MemberInfoPrinter();
        memberInfoPrinter.setMemberDao(memberDao);
        
        memberInfoPrinter.setMemberPrinter(memberPrinter);
        return memberInfoPrinter;
    }
    
    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
