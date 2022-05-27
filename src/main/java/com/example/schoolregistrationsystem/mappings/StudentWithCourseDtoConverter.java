package com.example.schoolregistrationsystem.mappings;

import com.example.schoolregistrationsystem.domain.Course;
import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.model.dto.StudentWithCourseDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class StudentWithCourseDtoConverter implements Converter<Student, StudentWithCourseDto> {

    @Override
    public StudentWithCourseDto convert(MappingContext<Student, StudentWithCourseDto> mappingContext) {
        Student student = mappingContext.getSource();
        return new StudentWithCourseDto(
                student.getId(),
                student.getName(),
                student.getSurname(),
                student.getCourses().stream().map(Course::getName).toList()
        );
    }
}
