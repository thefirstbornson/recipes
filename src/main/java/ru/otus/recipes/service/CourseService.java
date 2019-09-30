package ru.otus.recipes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.dto.CourseDto;
import ru.otus.recipes.repository.CourseRepository;
import ru.otus.recipes.service.mapper.CourseMapper;

@Slf4j
@Service
public class CourseService extends AbstractService <CourseDto, Course, CourseRepository, CourseMapper>{
    @Autowired
    public CourseService(CourseRepository repository, CourseMapper courseMapper) {
        super(repository, courseMapper, Course.class);
    }
}
