package com.example.schoolregistrationsystem.service;


import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.model.command.EditStudentCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentService {
    @Transactional
    Student createStudent(Student student);

    @Transactional(readOnly = true)
    Student findById(long id);

    @Transactional
    Student editStudent(Student student, EditStudentCommand studentCommand);

    void deleteById(long id);

    List<Student> findAll();

    List<Student> findAllStudentsWithCourse(long courseId);

    List<Student> findStudentsWhereCoursesAreNull();

}
