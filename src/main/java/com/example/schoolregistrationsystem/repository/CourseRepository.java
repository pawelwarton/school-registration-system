package com.example.schoolregistrationsystem.repository;

import com.example.schoolregistrationsystem.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
