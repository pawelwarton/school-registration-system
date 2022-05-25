package com.example.schoolregistrationsystem.model.dto;

import lombok.Value;

@Value
public class ValidationErrorDto {
    private String field;
    private String message;
}
