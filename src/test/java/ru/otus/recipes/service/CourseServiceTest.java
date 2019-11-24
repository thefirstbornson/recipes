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
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.CourseRepository;
import ru.otus.recipes.service.mapper.CourseMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class CourseServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Course persistedCourse;
    private CourseDto courseDto;
    private CourseDto persistedCourseDto;

    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private CourseMapper courseMapper;

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseService= new CourseService(courseRepository,courseMapper);
        Course course = new Course(0, "courseName");
        courseDto = new CourseDto("courseName");
        persistedCourse = new Course(ID,"courseName");
        persistedCourseDto = new CourseDto("courseName");
        Mockito.when(courseMapper.toEntity(any(CourseDto.class))).thenReturn(course);
    }

    @Test
    @DisplayName("Saving the course entity")
    void save() throws EntityExistsException {
        persistedCourseDto.setId(DTO_ID);
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(persistedCourse);
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(persistedCourseDto);
        assertEquals(DTO_ID,courseService.save(courseDto).getId());
    }

    @Test
    @DisplayName("Updating the course entity")
    void update() throws EntityNotFoundException {
        persistedCourse.setCourse("newCourseName");
        CourseDto persistedCourseDto = new CourseDto("newCourseName");
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(Optional.of(persistedCourse));
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(persistedCourse);
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(persistedCourseDto);
        assertEquals("newCourseName",courseService.update(courseDto).getCourse());
    }

    @Test
    @DisplayName("Finding the course entity by id")
    void findById() throws EntityNotFoundException {
        persistedCourseDto.setId(DTO_ID);
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedCourse));
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(persistedCourseDto);
        CourseDto resultDto = courseService.findById(1L);
        assertEquals(DTO_ID,resultDto.getId());
    }
}