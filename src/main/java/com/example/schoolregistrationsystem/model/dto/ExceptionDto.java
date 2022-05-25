package com.example.schoolregistrationsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionDto {

    USERNAME_NOT_FOUND("Username not found", HttpStatus.NOT_FOUND),
    STUDENT_NOT_FOUND("Student not found", HttpStatus.NOT_FOUND),

    COURSE_NOT_FOUND("Course not found", HttpStatus.NOT_FOUND),
    STUDENT_ALREADY_ENROLLED("Student already enrolled to course", HttpStatus.BAD_REQUEST),
    NO_AVAILABLE_PLACE("No available place for the course", HttpStatus.NO_CONTENT),
    COURSE_LIMIT_REACHED("The maximum number of courses is reached", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
