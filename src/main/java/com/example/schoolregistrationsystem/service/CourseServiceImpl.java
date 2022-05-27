package com.example.schoolregistrationsystem.service;


import com.example.schoolregistrationsystem.domain.Course;
import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.exception.ApplicationException;
import com.example.schoolregistrationsystem.model.dto.ExceptionDto;
import com.example.schoolregistrationsystem.repository.CourseRepository;
import com.example.schoolregistrationsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;

    @Override
    public Course createCourse(Course course) {
        course.setNumberOfPlacesForTheCourse(50);
        return courseRepository.saveAndFlush(course);
    }

    @Override
    public Course editCourse(long id, Course course) {
        Course editCourse = courseRepository.findById(id).orElseThrow(() -> new ApplicationException(ExceptionDto.COURSE_NOT_FOUND));
        editCourse.setName(course.getName());
        editCourse.setNumberOfPlacesForTheCourse(course.getNumberOfPlacesForTheCourse());
        return courseRepository.saveAndFlush(editCourse);
    }

    @Override
    public Course findById(long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ApplicationException(ExceptionDto.COURSE_NOT_FOUND));
    }

    @Override
    public void deleteById(long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ApplicationException(ExceptionDto.COURSE_NOT_FOUND));
        courseRepository.deleteById(course.getId());
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    @Override
    public void enrollStudentToCourse(Student student, long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ApplicationException(ExceptionDto.COURSE_NOT_FOUND));
        Student enrollStudent = studentService.findById(student.getId());

        if (course.getStudents().contains(enrollStudent)) {
            throw new ApplicationException(ExceptionDto.STUDENT_ALREADY_ENROLLED);
        } else if (course.getNumberOfActualStudents() == course.getNumberOfPlacesForTheCourse()) {
            throw new ApplicationException(ExceptionDto.NO_AVAILABLE_PLACE);
        } else if (enrollStudent.getNumerOfActualCourses() == enrollStudent.getMaxNumberOfCourses()) {
            throw new ApplicationException(ExceptionDto.COURSE_LIMIT_REACHED);
        }
        course.getStudents().add(enrollStudent);
        course.setNumberOfActualStudents(course.getNumberOfActualStudents() + 1);
        enrollStudent.setNumerOfActualCourses(enrollStudent.getNumerOfActualCourses() + 1);
    }

    @Override
    public List<Course> findAllStudentCourses(long studentId) {
        return courseRepository.findAllCoursesWithStudent(studentId);
    }

    @Override
    public List<Course> findCoursesWithoutStudents() {
        return courseRepository.findCoursesByStudentsNull();
    }

    @Override
    public void removeAll(List<Course> courses) {
        for (Course course : courses) {
            course.getStudents().clear();
        }
    }

    @Transactional
    @Override
    public void removeStudentFromCourse(long courseId, long studentId) {
        Course course = findById(courseId);
        Student student = studentService.findById(studentId);

        course.getStudents().remove(student);
    }
}
