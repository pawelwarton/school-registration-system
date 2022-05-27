package com.example.schoolregistrationsystem.controller;

import com.example.schoolregistrationsystem.domain.Course;
import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.model.Role;
import com.example.schoolregistrationsystem.model.command.CreateCourseCommand;
import com.example.schoolregistrationsystem.model.dto.CourseDto;
import com.example.schoolregistrationsystem.repository.CourseRepository;
import com.example.schoolregistrationsystem.service.CourseService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    private Student newStudent;

    @BeforeEach
    void setUp() {
        newStudent = Student.builder()
                .id(1L)
                .name("Adam")
                .surname("Malysz")
                .role(Role.ROLE_STUDENT)
                .username("malysz")
                .password("malysz")
                .email("malysz@gmail.com")
                .maxNumberOfCourses(5)
                .numerOfActualCourses(0)
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(newStudent, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Transactional
    @Test
    void shouldGetAllCoursesWithoutStudent() throws Exception {

        List<Course> courseList =  courseService.findAllCourses();
        courseService.removeAll(courseList);

        postman.perform(put("/course/1/enroll"))
                .andExpect(status().isOk());

        postman.perform(put("/course/2/enroll"))
                .andExpect(status().isOk());

        postman.perform(put("/course/3/enroll"))
                .andExpect(status().isOk());

        String response = postman.perform(get("/course/coursesWithoutStudents"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CourseDto> courses = objectMapper.readValue(response, new TypeReference<>() {
        });

        assertEquals(4, courses.size());
    }

    @Transactional
    @Test
    void shouldFindAllCoursesWithStudent() throws Exception {

        List<Course> courseList =  courseService.findAllCourses();
        courseService.removeAll(courseList);

        postman.perform(put("/course/3/enroll"))
                .andExpect(status().isOk());

        postman.perform(put("/course/4/enroll"))
                .andExpect(status().isOk());

        postman.perform(put("/course/5/enroll"))
                .andExpect(status().isOk());

        String responseJson = postman.perform(get("/course/" + newStudent.getId() + "/student"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CourseDto> courses = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertEquals(3, courses.size());
    }

    @Test
    void shouldEnrollStudentToCourse() throws Exception {
        postman.perform(put("/course/1/enroll"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRemoveStudentFromCourse() throws Exception {
        postman.perform(put("/course/2/enroll"))
                .andExpect(status().isOk());

        postman.perform(delete("/course/2/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCourse() throws Exception {
        CreateCourseCommand courseCommand = new CreateCourseCommand("Python", 50);

        String request = objectMapper.writeValueAsString(courseCommand);

        postman.perform(post("/course/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Python"));

        Course course = courseRepository.findByName(courseCommand.getName());

        postman.perform(delete("/course/" + course.getId() + "/delete"))
                .andExpect(status().isOk());
    }

}