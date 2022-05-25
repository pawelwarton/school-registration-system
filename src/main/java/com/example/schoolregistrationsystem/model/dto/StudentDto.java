package com.example.schoolregistrationsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StudentDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private int numberOfActualCourses;
}
