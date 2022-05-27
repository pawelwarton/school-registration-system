package com.example.schoolregistrationsystem.repository;

import com.example.schoolregistrationsystem.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT s FROM Student s JOIN FETCH s.courses c WHERE c.id=:courseId")
    List<Student> findAllStudentWithCourse(@Param("courseId") long courseId);

    List<Student> findStudentsByCoursesNull();
}
