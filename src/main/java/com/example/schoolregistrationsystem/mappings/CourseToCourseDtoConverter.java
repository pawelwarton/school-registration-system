package com.example.schoolregistrationsystem.mappings;

import com.example.schoolregistrationsystem.domain.Course;
import com.example.schoolregistrationsystem.model.dto.CourseDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class CourseToCourseDtoConverter implements Converter<Course, CourseDto> {

    @Override
    public CourseDto convert(MappingContext<Course, CourseDto> mappingContext) {
        Course course = mappingContext.getSource();
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getNumberOfPlacesForTheCourse(),
                course.getNumberOfActualStudents()
        );
    }
}
