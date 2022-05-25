package com.example.schoolregistrationsystem.model.command;

import com.example.schoolregistrationsystem.model.Role;
import lombok.Builder;
import lombok.Value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class CreateStudentCommand {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    private int maxNumberOfCourses;
}
