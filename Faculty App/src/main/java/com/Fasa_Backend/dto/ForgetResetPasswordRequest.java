package com.Fasa_Backend.dto;


public class ForgetResetPasswordRequest {
    private Long id;
    private String uniEmail;
    private String token;
    private String password;

    public ForgetResetPasswordRequest() {
    }

    public ForgetResetPasswordRequest(Long id,String uniEmail, String token, String password) {
        this.id = id;
        this.token = token;
        this.password = password;
        this.uniEmail = uniEmail;
    }

    public Long getId(){
        return id;
    }

    public String getUniEmail() {
        return uniEmail;
    }

    public void setUniEmail(String uniEmail) {
        this.uniEmail = uniEmail;
    }

    public String getToken() {
        return token;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
