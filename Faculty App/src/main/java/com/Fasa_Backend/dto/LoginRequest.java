package com.Fasa_Backend.dto;

public class LoginRequest {
    private  Long id;
    private String uniEmail;
    private String password;


    public LoginRequest(Long id, String uniEmail, String password) {
        this.id = id;
        this.uniEmail = uniEmail;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniEmail() {
        return uniEmail;
    }

    public void setUniEmail(String uniEmail) {
        this.uniEmail = uniEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
