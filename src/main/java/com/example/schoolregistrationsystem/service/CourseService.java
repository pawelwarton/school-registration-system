package com.example.schoolregistrationsystem.service;

import com.example.schoolregistrationsystem.domain.Course;
import com.example.schoolregistrationsystem.domain.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course);

    Course editCourse(long id, Course course);

    @Transactional(readOnly = true)
    Course findById(long id);

    void deleteById(long id);

    List<Course> findAllCourses();

    void enrollStudentToCourse(Student student, long courseId);

    List<Course> findCoursesWithoutStudents(int number);

}
