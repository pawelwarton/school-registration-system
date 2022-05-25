package com.example.schoolregistrationsystem.mappings;

import com.example.schoolregistrationsystem.domain.Student;
import com.example.schoolregistrationsystem.model.dto.StudentDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;


@Service
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(MappingContext<Student, StudentDto> mappingContext) {
        Student student = mappingContext.getSource();
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getNumerOfActualCourses()
        );
    }
}
