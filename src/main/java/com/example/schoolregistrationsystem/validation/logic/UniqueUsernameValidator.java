package com.example.schoolregistrationsystem.validation.logic;


import com.example.schoolregistrationsystem.repository.StudentRepository;
import com.example.schoolregistrationsystem.validation.annotation.UniqueUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !studentRepository.existsByUsername(username);
    }
}
