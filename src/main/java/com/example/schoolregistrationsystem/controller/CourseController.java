package com.example.schoolregistrationsystem.controller;

import com.example.schoolregistrationsystem.domain.Course;
import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.model.command.CreateCourseCommand;
import com.example.schoolregistrationsystem.model.dto.CourseDto;
import com.example.schoolregistrationsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CreateCourseCommand courseCommand) {
        final Course course = modelMapper.map(courseCommand, Course.class);
        return new ResponseEntity<>(modelMapper.map(courseService.createCourse(course), CourseDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/find")
    public ResponseEntity<CourseDto> findCourseById(@PathVariable("id") long id) {
        final Course course = courseService.findById(id);
        return new ResponseEntity<>(modelMapper.map(course, CourseDto.class), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<Course> courses = courseService.findAllCourses();
        return new ResponseEntity<>(courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).toList(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteCourse(@PathVariable("id") long id) {
        courseService.deleteById(id);
        return HttpStatus.OK;
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<CourseDto> editCourse(@PathVariable("id") long id, @RequestBody Course editCourse) {
        return new ResponseEntity<>(modelMapper.map(courseService.editCourse(id, editCourse), CourseDto.class), HttpStatus.OK);
    }

    @PutMapping("/{id}/enroll")
    public HttpStatus enrollStudentToCourse(@AuthenticationPrincipal Student student, @PathVariable("id") long courseId) {
        courseService.enrollStudentToCourse(student, courseId);
        return HttpStatus.OK;
    }

    @GetMapping("/coursesWithoutStudents")
    public ResponseEntity<List<CourseDto>> getAllCoursesWithoutStudents(){
        List<Course> courses = courseService.findCoursesWithoutStudents(0);
        return new ResponseEntity<>(courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).toList(), HttpStatus.OK);
    }
}
