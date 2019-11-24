package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.dto.CourseDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
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