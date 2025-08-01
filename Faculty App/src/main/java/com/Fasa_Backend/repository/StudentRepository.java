package com.Fasa_Backend.repository;

import com.Fasa_Backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByUniEmail(String uniEmail);

    Student findByUniEmail(String uniEmail);
}
