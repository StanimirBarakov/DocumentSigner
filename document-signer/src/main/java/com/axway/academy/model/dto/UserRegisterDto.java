package com.axway.academy.model.dto;

public class UserRegisterDto {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private int type;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String username, String password, String confirmPassword, String email, int type) {
        super();
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public String getEmail() {
        return email;
    }
    public int getType() {
        return type;
    }
}
