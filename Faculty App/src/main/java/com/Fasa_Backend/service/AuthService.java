package com.Fasa_Backend.service;

import com.Fasa_Backend.config.JwtUtil;
import com.Fasa_Backend.dto.LoginRequest;
import com.Fasa_Backend.dto.RegistrationRequest;
import com.Fasa_Backend.dto.ForgetResetPasswordRequest;
import com.Fasa_Backend.model.PasswordResetToken;
import com.Fasa_Backend.model.Student;
import com.Fasa_Backend.repository.PasswordResetTokenRepository;
import com.Fasa_Backend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class AuthService {


    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final EmailService emailService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailService emailService, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }







// Registration logic
public ResponseEntity<?> register(RegistrationRequest request){
    Map<String, Object> response = new HashMap<>();

    if (studentRepository.existsByUniEmail(request.getUniEmail())) {
        response.put("error", "Email already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409 is better than 302
    }

    if (!request.getUniEmail().matches("^[A-Z]{5}\\d{5}@ttu\\.edu\\.gh$")) {
        response.put("error", "Email must be a valid TTU email (e.g. BCICT22000@ttu.edu.gh)");
        return ResponseEntity.badRequest().body(response);
    }

    if (!request.getStudentId().matches("^[A-Z]{2}/[A-Z]{3}/\\d{2}/\\d{3}$")) {
        response.put("error", "Student ID must be in the format BC/ICT/22/352");
        return ResponseEntity.badRequest().body(response);
    }

    if(!request.getPassword().equals(request.getConfirmPassword())){
        response.put("error", "Password does not match");
        return ResponseEntity.badRequest().body(response);
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());

    Student newStudent = new Student();
    newStudent.setName(request.getName());
    newStudent.setStudentId(request.getStudentId());
    newStudent.setUniEmail(request.getUniEmail());
    newStudent.setPassword(encodedPassword);

    String token = UUID.randomUUID().toString();

    PasswordResetToken verificationToken = new PasswordResetToken();
    verificationToken.setToken(token);
    verificationToken.setUniEmail(newStudent.getUniEmail());
    verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
    verificationToken.setTokenType(PasswordResetToken.TokenType.EMAIL_VERIFICATION);
    passwordResetTokenRepository.save(verificationToken);

    emailService.sendVerificationEmail(newStudent.getUniEmail(), newStudent.getName(), token);



    studentRepository.save(newStudent);
    response.put("message", "Registration Successful");
    return ResponseEntity.ok(response);
}






//Verification Logic
    public ResponseEntity<?> verifyUser(String token) {
        PasswordResetToken verificationToken = passwordResetTokenRepository.findByToken(token);

        if (verificationToken == null
                || verificationToken.getTokenType() != PasswordResetToken.TokenType.EMAIL_VERIFICATION
                || verificationToken.isExpired()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token."));
        }

        String email = verificationToken.getUniEmail();
        Student student = studentRepository.findByUniEmail(email);
        if (student == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Student not found."));
        }

        student.setVerified(true);
        studentRepository.save(student);

        passwordResetTokenRepository.delete(verificationToken);

        return ResponseEntity.ok(Map.of("message", "Email verified successfully. You can now log in."));
    }







//Login logic
    public ResponseEntity<?> login(LoginRequest request){
        Map<String, Object> response = new HashMap<>();
        Student student = studentRepository.findByUniEmail(request.getUniEmail());

        if (!request.getUniEmail().matches("^[A-Z]{5}\\d{5}@ttu\\.edu\\.gh$")) {
            response.put("error", "Email must be a valid TTU email (e.g. BCICT22000@ttu.edu.gh)");
            return ResponseEntity.badRequest().body(response);
        }

        if (student != null && passwordEncoder.matches(request.getPassword(),student.getPassword())){
            String token = jwtUtil.generateToken(student.getUniEmail());
            response.put("message","Login Successful");
            response.put("token", token);
            response.put("id", student.getId());
        }
        else {
            response.put("error", "Invalid Credentials");
        }

        return ResponseEntity.ok(response);
    }







//Forget Password logic
    public ResponseEntity<?> forgotPassword(ForgetResetPasswordRequest request) {
        Map<String, String> response = new HashMap<>();
        String email = request.getUniEmail();

        if (email == null || !email.matches("^[A-Z]{5}\\d{5}@ttu\\.edu\\.gh$")) {
            response.put("error", "Email must be a valid TTU email (e.g. BCICT22000@ttu.edu.gh)");
            return ResponseEntity.badRequest().body(response);
        }

        Student student = studentRepository.findByUniEmail(email);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(null, token, email, LocalDateTime.now().plusMinutes(15));
        passwordResetTokenRepository.save(resetToken);

        String resetUrl = "http://yourfrontend.com/reset-password?token=" + token;
        emailService.sendResetEmail(email, resetUrl);

        response.put("message", "Password reset link has been sent to your University email!");

        return ResponseEntity.ok(response);
    }







//Reset Password logic
    public ResponseEntity<?> resetPassword(ForgetResetPasswordRequest request) {
        Map<String, Object> response = new HashMap<>();
        String token = request.getToken();
        String newPassword = request.getPassword();

        if (newPassword == null || newPassword.length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 8 characters");
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reset token");
        }

        if (resetToken.isExpired()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired");
        }

        String uniEmail = resetToken.getUniEmail();


        if (uniEmail == null || !uniEmail.matches("^[A-Z]{5}\\d{5}@ttu\\.edu\\.gh$")) {
            response.put("error", "Email must be a valid TTU email (e.g. BCICT22000@ttu.edu.gh)");
            return ResponseEntity.badRequest().body(response);
        }

        Student student = studentRepository.findByUniEmail(uniEmail);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        student.setPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);
        passwordResetTokenRepository.delete(resetToken);

        response.put("message", "Password reset successful");

        return ResponseEntity.ok(response);
    }

}
