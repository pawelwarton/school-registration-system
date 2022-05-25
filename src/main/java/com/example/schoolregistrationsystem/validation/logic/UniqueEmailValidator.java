package com.example.schoolregistrationsystem.validation.logic;

import com.example.schoolregistrationsystem.repository.StudentRepository;
import com.example.schoolregistrationsystem.validation.annotation.UniqueEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !studentRepository.existsByEmail(email);
    }
}
