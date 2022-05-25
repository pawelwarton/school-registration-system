package com.example.schoolregistrationsystem.repository;

import com.example.schoolregistrationsystem.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByNumberOfActualStudentsLessThanEqual(int number);

}

