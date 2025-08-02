package com.Fasa_Backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String uniEmail;

    private LocalDateTime expiryDate;

    public enum TokenType {
        PASSWORD_RESET,
        EMAIL_VERIFICATION
    }


    @Enumerated(EnumType.STRING)
    private TokenType tokenType; // NEW: To distinguish purpose


    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

    public PasswordResetToken() {
    }

    public PasswordResetToken(Long id, String token, String uniEmail, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.uniEmail = uniEmail;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUniEmail() {
        return uniEmail;
    }

    public void setUniEmail(String uniEmail) {
        this.uniEmail = uniEmail;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public TokenType getTokenType() { return tokenType; }
    public void setTokenType(TokenType tokenType) { this.tokenType = tokenType; }
}
