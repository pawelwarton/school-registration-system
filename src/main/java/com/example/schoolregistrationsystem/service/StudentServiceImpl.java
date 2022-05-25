package com.example.schoolregistrationsystem.service;

import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.exception.ApplicationException;
import com.example.schoolregistrationsystem.model.Role;
import com.example.schoolregistrationsystem.model.command.EditStudentCommand;
import com.example.schoolregistrationsystem.model.dto.ExceptionDto;
import com.example.schoolregistrationsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Student createStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.ROLE_STUDENT);
        student.setMaxNumberOfCourses(5);
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ApplicationException(ExceptionDto.STUDENT_NOT_FOUND));
    }

    @Override
    public Student editStudent(Student student, EditStudentCommand studentCommand) {
        student.setEmail(studentCommand.getNewEmail());
        student.setVersion(studentCommand.getActualVersion());
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public void deleteById(long id) {
        final Student student = studentRepository.findById(id).orElseThrow(() -> new ApplicationException(ExceptionDto.STUDENT_NOT_FOUND));
        studentRepository.deleteById(student.getId());
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findStudentsWithoutCourses(int number) {
        return studentRepository.findByNumerOfActualCourses(number);
    }
}
