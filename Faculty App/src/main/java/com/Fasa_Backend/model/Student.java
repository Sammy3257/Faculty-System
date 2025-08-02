package com.Fasa_Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String studentId;
    public String uniEmail;
    public String password;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isVerified = false;




    public Student() {
    }

    public Student(Long id, String name, String studentId, String uniEmail, String password,boolean isVerified) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.uniEmail = uniEmail;
        this.password = password;
        this.isVerified = isVerified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
