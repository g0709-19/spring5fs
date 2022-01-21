package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import assembler.Assembler;
import config.AppContext;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.VersionPrinter;
import spring.WrongIdPasswordException;

public class MainForSpring {
    private static ApplicationContext context = null;
    
    public static void main(String[] args) throws IOException {
        context = new AnnotationConfigApplicationContext(AppContext.class);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("명령어를 입력하세요: ");
            String command = reader.readLine();
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다.");
                break;
            }
            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            } else if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            } else if (command.startsWith("list")) {
                processListCommand();
                continue;
            } else if (command.startsWith("info ")) {
                processInfoCommand(command.split(" "));
                continue;
            } else if (command.equalsIgnoreCase("version")) {
                processVersionCommand();
                continue;
            }
            printHelp();
        }
    }
    
    private static Assembler assembler = new Assembler();
    
    private static void processNewCommand(String[] args) {
        if (args.length != 5) {
            printHelp();
            return;
        }
        MemberRegisterService registerService = context.getBean("memberRegisterService", MemberRegisterService.class);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(args[1]);
        registerRequest.setName(args[2]);
        registerRequest.setPassword(args[3]);
        registerRequest.setConfirmPassword(args[4]);
        
        if (!registerRequest.isPasswordEqualToConfirmPassword()) {
            System.out.println("암호와 확인이 일치하지 않습니다.\n");
            return;
        }
        try {
            registerService.regist(registerRequest);
            System.out.println("등록했습니다.\n");
        } catch (DuplicateMemberException e) {
            System.out.println("이미 존재하는 이메일입니다.\n");
        }
    }
    
    private static void processChangeCommand(String[] args) {
        if (args.length != 4) {
            printHelp();
            return;
        }
        ChangePasswordService changePasswordService = context.getBean("changePasswordService", ChangePasswordService.class);
        try {
            changePasswordService.changePassword(args[1], args[2], args[3]);
            System.out.println("암호를 변경했습니다.\n");
        } catch (MemberNotFoundException e) {
            System.out.println("존재하지 않는 이메일입니다.\n");
        } catch (WrongIdPasswordException e) {
            System.out.println("이메일과 암호가 일치하지 않습니다.\n");
        }
    }
    
    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법:");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }
    
    private static void processListCommand() {
        MemberListPrinter memberListPrinter = context.getBean("memberListPrinter", MemberListPrinter.class);
        memberListPrinter.printAll();
    }
    
    private static void processInfoCommand(String[] args) {
        if (args.length != 2) {
            printHelp();
            return;
        }
        MemberInfoPrinter memberInfoPrinter = context.getBean("memberInfoPrinter", MemberInfoPrinter.class);
        memberInfoPrinter.printMemberInfo(args[1]);
        // 여기서 만약 memberInfoPrinter.setMemberInfo() 로 바꾸게 된다면?
        // 강제적으로 막을 방법은 없을까?
    }
    
    private static void processVersionCommand() {
        VersionPrinter versionPrinter = context.getBean("versionPrinter", VersionPrinter.class);
        versionPrinter.print();
    }
}
