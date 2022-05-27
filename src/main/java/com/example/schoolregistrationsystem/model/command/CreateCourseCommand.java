package com.example.schoolregistrationsystem.model.command;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseCommand {
    @NotEmpty
    private String name;
    @Size(max = 50)
    private int numberOfPlacesForTheCourse;
}
