package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.dto.MealDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.service.mapper.MealMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MealServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Meal persistedEntity;
    private MealDto dto;
    private MealDto persistedDto;

    @MockBean
    private MealRepository repository;
    @MockBean
    private MealMapper mapper;
    private MealService service;

    @BeforeEach
    void setUp() {
        service = new MealService(repository, mapper);
        Meal Meal = new Meal(0, "MealName");
        dto = new MealDto("MealName");
        persistedEntity = new Meal(ID,"MealName");
        persistedDto = new MealDto("MealName");
        Mockito.when(mapper.toEntity(any(MealDto.class))).thenReturn(Meal);
    }

    @Test
    @DisplayName("Saving the Meal entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(Meal.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Meal.class))).thenReturn(persistedDto);
        assertEquals(DTO_ID, service.save(dto).getId());
    }

    @Test
    @DisplayName("Updating the Meal entity")
    void update() throws EntityNotFoundException {
       persistedDto.setMeal("newMealName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Meal.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Meal.class))).thenReturn(persistedDto);
        assertEquals("newMealName", service.update(dto).getMeal());
    }

    @Test
    @DisplayName("Finding the Meal entity by id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Meal.class))).thenReturn(persistedDto);
        MealDto resultDto = service.findById(ID);
        assertEquals(DTO_ID,resultDto.getId());
    }

    @Test
    @DisplayName("Ошибка при сохранении существующей entity")
    void saveEntityExistsException() {
        dto.setId(DTO_ID);
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