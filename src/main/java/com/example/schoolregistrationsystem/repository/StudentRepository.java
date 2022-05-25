package com.example.schoolregistrationsystem.repository;

import com.example.schoolregistrationsystem.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
    List<Student> findByNumerOfActualCourses(int number);

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
