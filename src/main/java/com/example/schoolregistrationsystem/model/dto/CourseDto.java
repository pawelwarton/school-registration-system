package com.example.schoolregistrationsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CourseDto {

    private Long id;
    private String name;
    private int maximumNumberOfStudents;
    private int numberOfActualStudents;
}
