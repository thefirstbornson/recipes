package ru.otus.recipes.service.mapper;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.dto.CourseDto;

@Service
public class CourseMapper extends AbstractMapper<CourseDto, Course> {
    CourseMapper() {
        super(Course.class, CourseDto.class);
    }
}
