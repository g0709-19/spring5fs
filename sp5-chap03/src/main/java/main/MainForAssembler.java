package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import assembler.Assembler;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.WrongIdPasswordException;

public class MainForAssembler {
  public static void main(String[] args) throws IOException {
    /*
     * 이클립스에서 실행할 경우 System.console() 이 null 을 리턴하므로 System.in 을 이용해서 콘솔 입력을 받는다. <- 무슨 말인지 모르겠다.
     */
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
    MemberRegisterService registerService = assembler.getMemberRegisterService();
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
    ChangePasswordService changePasswordService = assembler.getChangePasswordService();
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
}
