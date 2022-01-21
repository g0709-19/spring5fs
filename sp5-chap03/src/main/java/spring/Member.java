package spring;

import java.time.LocalDateTime;

public class Member {
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime registerDateTime;
    
    public Member(String email, String password, String name, LocalDateTime regDateTime) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.registerDateTime = regDateTime;
    }
    
    void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }
    
    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();  
        /*
         * Unhandled exception type WrongIdPasswordException
         * 왜 그냥 Exception 을 상속했을 때는 throws 를 하라고 했을까?
         */
        this.password = newPassword;
    }
}
