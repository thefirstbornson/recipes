package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.dto.CourseDto;
import ru.otus.recipes.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController extends AbstractController<Course, CourseService, CourseDto> {
    public CourseController(CourseService service) {
        super(service, Course.class);
    }
}