package com.example.schoolregistrationsystem.exception;

import com.example.schoolregistrationsystem.model.dto.ExceptionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private ExceptionDto exceptionDto;

}
