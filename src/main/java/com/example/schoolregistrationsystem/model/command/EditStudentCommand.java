package com.example.schoolregistrationsystem.model.command;

import lombok.Value;

@Value
public class EditStudentCommand {
    private String newEmail;
    private int actualVersion;
}
