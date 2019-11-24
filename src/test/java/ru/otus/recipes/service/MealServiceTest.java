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
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.service.mapper.MealMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MealServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Meal persistedMeal;
    private MealDto MealDto;
    private MealDto persistedMealDto;

    @MockBean
    private MealRepository mealRepository;
    @MockBean
    private MealMapper mealMapper;
    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealService = new MealService(mealRepository, mealMapper);
        Meal Meal = new Meal(0, "MealName");
        MealDto = new MealDto("MealName");
        persistedMeal = new Meal(ID,"MealName");
        persistedMealDto = new MealDto("MealName");
        Mockito.when(mealMapper.toEntity(any(MealDto.class))).thenReturn(Meal);
    }

    @Test
    @DisplayName("Saving the Meal entity")
    void save() throws EntityExistsException {
        persistedMealDto.setId(DTO_ID);
        Mockito.when(mealRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(mealRepository.save(any(Meal.class))).thenReturn(persistedMeal);
        Mockito.when(mealMapper.toDto(any(Meal.class))).thenReturn(persistedMealDto);
        assertEquals(DTO_ID,mealService.save(MealDto).getId());
    }

    @Test
    @DisplayName("Updating the Meal entity")
    void update() throws EntityNotFoundException {
       persistedMealDto.setMeal("newMealName");
        Mockito.when(mealRepository.findById(anyLong())).thenReturn(Optional.of(persistedMeal));
        Mockito.when(mealRepository.save(any(Meal.class))).thenReturn(persistedMeal);
        Mockito.when(mealMapper.toDto(any(Meal.class))).thenReturn(persistedMealDto);
        assertEquals("newMealName",mealService.update(MealDto).getMeal());
    }

    @Test
    @DisplayName("Finding the Meal entity by id")
    void findById() throws EntityNotFoundException {
        persistedMealDto.setId(DTO_ID);
        Mockito.when(mealRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedMeal));
        Mockito.when(mealMapper.toDto(any(Meal.class))).thenReturn(persistedMealDto);
        MealDto resultDto = mealService.findById(ID);
        assertEquals(DTO_ID,resultDto.getId());
    }
}