package com.Fasa_Backend.controller;

import com.Fasa_Backend.dto.LoginRequest;
import com.Fasa_Backend.dto.RegistrationRequest;
import com.Fasa_Backend.dto.ForgetResetPasswordRequest;
import com.Fasa_Backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request){
        return authService.register(request);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }


    @PostMapping("forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetResetPasswordRequest request){
    return authService.forgotPassword(request);
    }


    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ForgetResetPasswordRequest request){
        return authService.resetPassword(request);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }


}
