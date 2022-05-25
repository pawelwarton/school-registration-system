package com.example.schoolregistrationsystem.controller;

import com.example.schoolregistrationsystem.exception.ApplicationException;
import com.example.schoolregistrationsystem.exception.ErrorMessage;
import com.example.schoolregistrationsystem.model.dto.ValidationErrorDto;
import lombok.Builder;
import lombok.Value;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(ApplicationException e) {
        return ResponseEntity.status(e.getExceptionDto().getHttpStatus())
                .body(new ErrorMessage(e.getExceptionDto().getMessage()));
    }

    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity handleStaleObjectStateException(StaleObjectStateException e) {
        String entityName = e.getEntityName().substring(e.getEntityName().lastIndexOf('.') + 1);
        return ResponseEntity.badRequest().body(new OptimisticLockError(entityName, (Long) e.getIdentifier()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationErrorDto> errors = e.getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorDto(
                        fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Value
    @Builder
    static class OptimisticLockError {
        private String entityName;
        private long id;
        private final String errorCode = "VERSION_TO_OLD";
    }

}
