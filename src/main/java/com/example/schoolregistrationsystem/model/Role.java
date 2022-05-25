package com.example.schoolregistrationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String message;
}
