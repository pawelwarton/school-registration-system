package com.example.schoolregistrationsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class StudentWithCourseDto {
    private Long id;
    private String name;
    private String surname;
    private List<String> courses;
}
