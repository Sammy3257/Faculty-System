package com.Fasa_Backend.repository;

import com.Fasa_Backend.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
        PasswordResetToken findByToken(String token);
    }

