package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.*;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.RecipeIngredientRepository;
import ru.otus.recipes.repository.RecipeRepository;
import ru.otus.recipes.service.mapper.RecipeMapper;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class RecipeServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private static final String RECIPE_NAME = "testRecipe";
    private static final String RECIPE_DESCRIPTION = "testDescription";
    private static final String INSTRUCTIONS = "testInstructions";
    private static final Integer COOK_TIME = 30;
    private static final LevelDto LEVEL_DTO = new LevelDto(1);
    private static final CuisineDto CUISINE_DTO = new CuisineDto("testCuisine");
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "testImagePath";
    private Recipe persistedEntity;
    private RecipeDto dto;
    private RecipeDto persistedDto;

    @MockBean
    private RecipeRepository repository;
    @MockBean
    private RecipeMapper mapper;
    @MockBean
    private RecipeIngredientRepository recipeIngredientRepository;
    private RecipeService service;

    @BeforeEach
    void setUp() {
        NutritionalInformationDto nutritionalInformationDto = new NutritionalInformationDto("testNutrition");
        IngredientNutritionalDto ingredientNutritionalDto = new IngredientNutritionalDto(nutritionalInformationDto,1);
        IngredientDto ingredientDto= new IngredientDto("testIngredient", Collections.singletonList(ingredientNutritionalDto));
        MeasurementDto measurementDto = new MeasurementDto("testMeasurement");
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(ingredientDto,1,measurementDto);
        FoodCategoryDto foodCategoryDto = new FoodCategoryDto("testFoodCategory");
        CourseDto courseDto = new CourseDto("testCourse");
        MealDto mealDto = new MealDto("testMeal");
        service = new RecipeService(repository, recipeIngredientRepository, mapper);
        Recipe recipe = new Recipe();
        dto =  new RecipeDto(0,RECIPE_NAME,RECIPE_DESCRIPTION,INSTRUCTIONS,COOK_TIME, LEVEL_DTO, CUISINE_DTO,RATING,IMAGE_PATH,
                Collections.singletonList(recipeIngredientDto), Collections.singletonList(courseDto), Collections.singletonList(foodCategoryDto),
                Collections.singletonList(mealDto));
        persistedEntity = new Recipe();
        persistedDto = new RecipeDto(DTO_ID,RECIPE_NAME,RECIPE_DESCRIPTION,INSTRUCTIONS,COOK_TIME, LEVEL_DTO, CUISINE_DTO,RATING,IMAGE_PATH,
                Collections.singletonList(recipeIngredientDto), Collections.singletonList(courseDto), Collections.singletonList(foodCategoryDto),
                Collections.singletonList(mealDto));
        Mockito.when(mapper.toEntity(any(RecipeDto.class))).thenReturn(recipe);
    }

    @Test
    @DisplayName("Сохранение Recipe entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(Recipe.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Recipe.class))).thenReturn(persistedDto);
        assertEquals(DTO_ID, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление recipe entity")
    void update() throws EntityNotFoundException {
        persistedDto.setName("newRecipeName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Recipe.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Recipe.class))).thenReturn(persistedDto);
        assertEquals("newRecipeName", service.update(dto).getName());
    }

    @Test
    @DisplayName("Поиск the recipe entity по id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(1);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Recipe.class))).thenReturn(persistedDto);
        RecipeDto resultDto = service.findById(1L);
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

    @Test
    @DisplayName("Ошибка при удалении несуществующей entity")
    void deleteByIdEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteById(DTO_ID));
    }
}