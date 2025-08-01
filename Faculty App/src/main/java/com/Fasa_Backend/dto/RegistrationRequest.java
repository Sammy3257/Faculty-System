package com.Fasa_Backend.dto;

public class RegistrationRequest {

    private String name;
    private String studentId;
     private String uniEmail;
     private String password;
     private String confirmPassword;


    public RegistrationRequest(String name, String studentId, String uniEmail, String password, String confirmPassword) {
        this.name = name;
        this.studentId = studentId;
        this.uniEmail = uniEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
