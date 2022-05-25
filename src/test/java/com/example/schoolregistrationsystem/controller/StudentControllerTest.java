package com.example.schoolregistrationsystem.controller;

import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.model.Role;
import com.example.schoolregistrationsystem.model.command.CreateStudentCommand;
import com.example.schoolregistrationsystem.model.command.EditStudentCommand;
import com.example.schoolregistrationsystem.model.dto.StudentDto;
import com.example.schoolregistrationsystem.repository.StudentRepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc postman;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        Student newStudent = Student.builder()
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

    @Test
    void shouldCreateStudent() throws Exception {
        CreateStudentCommand studentCommand = CreateStudentCommand.builder()
                .name("Jan")
                .surname("Nowak")
                .role(Role.ROLE_STUDENT)
                .username("nowak")
                .password("nowak")
                .email("nowak@gmail.com")
                .maxNumberOfCourses(5)
                .build();

        String request = objectMapper.writeValueAsString(studentCommand);

        postman.perform(post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.email").value("nowak@gmail.com"));

        Student student = studentRepository.findByEmail(studentCommand.getEmail());

        assertEquals("nowak", student.getUsername());
    }

    @Test
    void shouldEditStudent() throws Exception {
        CreateStudentCommand studentCommand = CreateStudentCommand.builder()
                .name("Robert")
                .surname("Kubica")
                .role(Role.ROLE_STUDENT)
                .username("kubica")
                .password("kubica")
                .email("kubica@gmail.com")
                .maxNumberOfCourses(5)
                .build();

        String request = objectMapper.writeValueAsString(studentCommand);

        postman.perform(post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        Student student = studentRepository.findByEmail(studentCommand.getEmail());

        EditStudentCommand editStudentCommand = new EditStudentCommand("robert@gmail.com", 0);

        String editRequest = objectMapper.writeValueAsString(editStudentCommand);

        postman.perform(put("/student/" + student.getId() + " /edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editRequest))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        CreateStudentCommand studentCommand = CreateStudentCommand.builder()
                .name("Will")
                .surname("Smith")
                .role(Role.ROLE_STUDENT)
                .username("smith")
                .password("smith")
                .email("smith@gmail.com")
                .maxNumberOfCourses(5)
                .build();

        String request = objectMapper.writeValueAsString(studentCommand);

        postman.perform(post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        Student student = studentRepository.findByEmail(studentCommand.getEmail());

        postman.perform(delete("/student/" + student.getId() + "/delete"))
                .andExpect(status().isOk());

        Student deletedStudent = studentRepository.findByEmail(studentCommand.getEmail());

        assertNull(deletedStudent);
    }

    @Test
    void shouldFindStudentById() throws Exception {
        Student student = studentRepository.findByEmail("kowalski@gmail.com");

        postman.perform(get("/student/" + student.getId() + "/find"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dawid"));
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        String responseJson = postman.perform(get("/student/getAll"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<StudentDto> studentResponse = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        Student s1 = studentRepository.findByEmail("malysz@gmail.com");
        Student s2 = studentRepository.findByEmail("kowalski@gmail.com");
        Student s3 = studentRepository.findByEmail("mucha@gmail.com");
        Student s4 = studentRepository.findByEmail("nowak@gmail.com");

        List<StudentDto> expectedResponse = Arrays.asList(
                new StudentDto(s1.getId(), s1.getName(), s1.getSurname(), s1.getEmail(), s1.getNumerOfActualCourses()),
                new StudentDto(s2.getId(), s2.getName(), s2.getSurname(), s2.getEmail(), s2.getNumerOfActualCourses()),
                new StudentDto(s3.getId(), s3.getName(), s3.getSurname(), s3.getEmail(), s3.getNumerOfActualCourses()),
                new StudentDto(s4.getId(), s4.getName(), s4.getSurname(), s4.getEmail(), s4.getNumerOfActualCourses())
        );

        studentResponse.sort(Comparator.comparing(StudentDto::getName));
        expectedResponse.sort(Comparator.comparing(StudentDto::getName));

        assertEquals(expectedResponse, studentResponse);
        assertEquals(4, studentResponse.size());
    }

    @WithMockUser(username = "pacholek", password = "pacholek")
    @Test
    void shouldGetStudentsWithoutCourses() throws Exception {
        postman.perform(put("/course/1/enroll"))
                .andExpect(status().isOk());


        String responseJson = postman.perform(get("/student/studentsWithoutCourses"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<StudentDto> studentResponse = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        studentResponse.forEach(System.out::println);
    }

}