package com.example.schoolregistrationsystem.controller;

import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.model.command.CreateStudentCommand;
import com.example.schoolregistrationsystem.model.command.EditStudentCommand;
import com.example.schoolregistrationsystem.model.dto.StudentDto;
import com.example.schoolregistrationsystem.model.dto.StudentWithCourseDto;
import com.example.schoolregistrationsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<StudentDto> createStudent(@RequestBody @Valid CreateStudentCommand studentCommand) {
        Student student = modelMapper.map(studentCommand, Student.class);
        return new ResponseEntity<>(modelMapper.map(studentService.createStudent(student), StudentDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<StudentDto> editStudent(@PathVariable("id") long id, @RequestBody EditStudentCommand studentCommand) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(modelMapper.map(studentService.editStudent(student, studentCommand), StudentDto.class));
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteStudent(@PathVariable("id") long id) {
        studentService.deleteById(id);
        return HttpStatus.OK;
    }

    @GetMapping("/{id}/find")
    public ResponseEntity<StudentDto> findStudentById(@PathVariable("id") long id) {
        Student student = studentService.findById(id);
        return new ResponseEntity<>(modelMapper.map(student, StudentDto.class), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students.stream().map(student -> modelMapper.map(student, StudentDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}/getStudentsWithCourse")
    public ResponseEntity<List<StudentDto>> findAllStudentsWithCourse(@PathVariable("id") long id) {
        List<Student> students = studentService.findAllStudentsWithCourse(id);
        return new ResponseEntity<>(students.stream().map(student -> modelMapper.map(student, StudentDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/studentsWithoutCourses")
    public ResponseEntity<List<StudentDto>> findStudentsWithoutCourse() {
        List<Student> students = studentService.findStudentsWhereCoursesAreNull();
        return new ResponseEntity<>(students.stream().map(student -> modelMapper.map(student, StudentDto.class)).toList(), HttpStatus.OK);
    }

}
