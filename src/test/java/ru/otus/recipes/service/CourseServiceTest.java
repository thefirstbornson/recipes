package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.domain.Cuisine;
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
    private Course persistedEntity;
    private CourseDto dto;
    private CourseDto persistedDto;

    @MockBean
    private CourseRepository repository;
    @MockBean
    private CourseMapper mapper;
    private CourseService service;

    @BeforeEach
    void setUp() {
        service = new CourseService(repository, mapper);
        Course course = new Course(0, "courseName");
        dto = new CourseDto("courseName");
        persistedEntity = new Course(ID,"courseName");
        persistedDto = new CourseDto("courseName");
        Mockito.when(mapper.toEntity(any(CourseDto.class))).thenReturn(course);
//        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Course.class))).thenReturn(persistedEntity);
    }

    @Test
    @DisplayName("Сохранение course entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(mapper.toDto(any(Course.class))).thenReturn(persistedDto);
        assertEquals(DTO_ID, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление course entity")
    void update() throws EntityNotFoundException {
        persistedDto.setCourse("newCourseName");
        CourseDto persistedCourseDto = new CourseDto("newCourseName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Course.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Course.class))).thenReturn(persistedCourseDto);
        assertEquals("newCourseName",service.update(dto).getCourse());
    }

    @Test
    @DisplayName("Поиск course entity by id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Course.class))).thenReturn(persistedDto);
        CourseDto resultDto = service.findById(1L);
        assertEquals(DTO_ID,resultDto.getId());
    }

    @Test
    @DisplayName("Ошибка при сохранении существующей entity")
    void saveEntityExistsException() {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        assertThrows(EntityExistsException.class, () -> service.save(dto));
    }

    @Test
    @DisplayName("Ошибка при обновлении несуществующей entity")
    void updateEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("Ошибка при поиске несуществующей entity")
    void findByIdEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(DTO_ID));
    }
}