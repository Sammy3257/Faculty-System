package com.Fasa_Backend.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetEmail(String to, String token) {
        String subject = "Reset Your Password";
        String url = "http://localhost:8080/reset-password?token=" + token;

        String body = "Click the link to reset your password: " + url + "\n\n"
                + "If you didn't request a password reset, you can ignore this email.\n\n"
                + "Best regards,\n"
                + "Your Support Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
