package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.RecipeTestConfiguration;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.dto.CourseDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(properties= "spring.main.allow-bean-definition-overriding=true")
@ContextConfiguration (classes = {RecipeTestConfiguration.class})
class CourseMapperTest {

    private final Course course = new Course(0, "courseName");
    private final CourseDto courseDto = new CourseDto("courseName");

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void toEntity() {
      Course courseFromDto = courseMapper.toEntity(courseDto);
      assertEquals(courseFromDto.getId(),course.getId());
    }

    @Test
    void toDto() {
        CourseDto courseDto = courseMapper.toDto(course);
        assertEquals(courseDto.getCourse(),course.getCourse());
    }
}