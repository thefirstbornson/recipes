package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.repository.CourseRepository;

@Service
public class CourseService extends AbstractService <Course, CourseRepository>{
    public CourseService(CourseRepository repository) {
        super(repository);
    }
}
