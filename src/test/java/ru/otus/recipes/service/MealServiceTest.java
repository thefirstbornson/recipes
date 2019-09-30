package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.dto.MealDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.service.mapper.MealMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MealServiceTest {

    private Meal persistedMeal;
    private MealDto MealDto;
    private MealDto persistedMealDto;

    @MockBean
    private MealRepository MealRepository;
    @MockBean
    private MealMapper MealMapper;
    @Bean
    MealService MealService() {
        return new MealService(MealRepository, MealMapper);
    }

    @BeforeEach
    void setUp() {
        Meal Meal = new Meal(0, "MealName");
        MealDto = new MealDto("MealName");
        persistedMeal = new Meal(1,"MealName");
        persistedMealDto = new MealDto("MealName");
        Mockito.when(MealMapper.toEntity(any(MealDto.class))).thenReturn(Meal);
    }

    @Test
    @DisplayName("Saving the Meal entity")
    void save() {
        persistedMealDto.setId(1L);
        Mockito.when(MealRepository.save(any(Meal.class))).thenReturn(persistedMeal);
        Mockito.when(MealMapper.toDto(any(Meal.class))).thenReturn(persistedMealDto);
        assertEquals(1,MealService().save(MealDto).getId());
    }

    @Test
    @DisplayName("Updating the Meal entity")
    void update() {
       persistedMealDto.setMeal("newMealName");
        Mockito.when(MealRepository.save(any(Meal.class))).thenReturn(persistedMeal);
        Mockito.when(MealMapper.toDto(any(Meal.class))).thenReturn(persistedMealDto);
        assertEquals("newMealName",MealService().update(MealDto).getMeal());
    }

    @Test
    @DisplayName("Finding the Meal entity by id")
    void findById() throws EntityNotFoundException {
        persistedMealDto.setId(1);
        Mockito.when(MealRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedMeal));
        Mockito.when(MealMapper.toDto(any(Meal.class))).thenReturn(persistedMealDto);
        MealDto resultDto = MealService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}