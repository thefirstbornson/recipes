package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.dto.CourseDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.CourseRepository;
import ru.otus.recipes.service.mapper.CourseMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class CourseServiceTest {

    private Course persistedCourse;
    private CourseDto courseDto;
    private CourseDto persistedCourseDto;

    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private CourseMapper courseMapper;
    @Bean
    CourseService courseService() {
        return new CourseService(courseRepository,courseMapper);
    }

    @BeforeEach
    void setUp() {
        Course course = new Course(0, "courseName");
        courseDto = new CourseDto("courseName");
        persistedCourse = new Course(1,"courseName");
        persistedCourseDto = new CourseDto("courseName");
        Mockito.when(courseMapper.toEntity(any(CourseDto.class))).thenReturn(course);
    }

    @Test
    @DisplayName("Saving the course entity")
    void save() {
        persistedCourseDto.setId(1L);
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(persistedCourse);
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(persistedCourseDto);
        assertEquals(1,courseService().save(courseDto).getId());
    }

    @Test
    @DisplayName("Updating the course entity")
    void update() {
        persistedCourse.setCourse("newCourseName");
        CourseDto persistedCourseDto = new CourseDto("newCourseName");
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(persistedCourse);
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(persistedCourseDto);
        assertEquals("newCourseName",courseService().update(courseDto).getCourse());
    }

    @Test
    @DisplayName("Finding the course entity by id")
    void findById() throws EntityNotFoundException {
        persistedCourseDto.setId(1);
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedCourse));
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(persistedCourseDto);
        CourseDto resultDto = courseService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}