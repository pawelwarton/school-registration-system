package com.example.schoolregistrationsystem.model.command;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Builder
public class CreateCourseCommand {
    @NotEmpty
    private String name;
    @Size(max = 50)
    private int maximumNumberOfStudents;
}
