package com.example.schoolregistrationsystem.repository;

import com.example.schoolregistrationsystem.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c JOIN FETCH c.students s WHERE s.id=:studentId")
    List<Course> findAllCoursesWithStudent(@Param("studentId") Long studentId);

    List<Course> findCoursesByStudentsNull();

    Course findByName(String name);

}


