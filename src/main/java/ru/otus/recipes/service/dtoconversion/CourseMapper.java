package ru.otus.recipes.service.dtoconversion;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.dto.CourseDto;

@Service
public class CourseMapper extends AbstractMapper<CourseDto, Course> {
    CourseMapper() {
        super(Course.class, CourseDto.class);
    }
}
