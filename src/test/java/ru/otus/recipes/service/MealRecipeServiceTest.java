package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.dto.*;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRecipeRepository;
import ru.otus.recipes.service.mapper.MealRecipeMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MealRecipeServiceTest {
    private static final String RECIPE_NAME = "testRecipe";
    private static final String RECIPE_DESCRIPTION = "testDescription";
    private static final String INSTRUCTIONS = "testInstructions";
    private static final Integer COOK_TIME = 30;
    private static final LevelDto LEVEL_DTO = new LevelDto(1);
    private static final CuisineDto CUISINE_DTO = new CuisineDto("testCuisine");
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "testImagePath";
    private static final String MEAL_NAME = "anotherMealName";
    private static final Long ID = 1L;
    private static final Long DTO_ID = 1L;
    private static final Long DTO_ID_UPDATE = 2L;
    private MealRecipe persistedEntity;
    private MealDto mealDtoUpdate;
    private MealRecipeDto dto;
    private MealRecipeService service;

    @MockBean
    private MealRecipeRepository repository;
    @MockBean
    private MealRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        service = new MealRecipeService(repository, mapper);
        NutritionalInformationDto nutritionalInformationDto = new NutritionalInformationDto("testNutrition");
        IngredientNutritionalDto ingredientNutritionalDto = new IngredientNutritionalDto(nutritionalInformationDto,1);
        IngredientDto ingredientDto= new IngredientDto("testIngredient", Collections.singletonList(ingredientNutritionalDto));
        MeasurementDto measurementDto = new MeasurementDto("testMeasurement");
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(ingredientDto,1,measurementDto);
        FoodCategoryDto foodCategoryDto = new FoodCategoryDto("testFoodCategory");
        CourseDto courseDto = new CourseDto("testCourse");
        MealDto mealDto = new MealDto("testMeal");
        mealDtoUpdate = new MealDto(MEAL_NAME);
        RecipeDto recipeDto = new RecipeDto(0,RECIPE_NAME,RECIPE_DESCRIPTION,INSTRUCTIONS,COOK_TIME, LEVEL_DTO, CUISINE_DTO,RATING,IMAGE_PATH,
                Collections.singletonList(recipeIngredientDto), Collections.singletonList(courseDto), Collections.singletonList(foodCategoryDto),
                Collections.singletonList(mealDto));
        dto = new MealRecipeDto(mealDto, new HashSet<>(List.of(recipeDto)));
        dto.setId(ID);
        persistedEntity = new MealRecipe();
        persistedEntity.setId(ID);
        MealRecipe mealRecipe = new MealRecipe();
        Mockito.when(mapper.toEntity(any(MealRecipeDto.class))).thenReturn(mealRecipe);
    }

    @Test
    @DisplayName("Сохранение  MealRecipe entity")
    void save() throws EntityExistsException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(mapper.toDto(any(MealRecipe.class))).thenReturn(dto);
        Mockito.when(repository.save(any(MealRecipe.class))).thenReturn(persistedEntity);
        assertEquals(ID, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление mealRecipe entity")
    void update() throws EntityNotFoundException {
        dto.setMeal(mealDtoUpdate);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(MealRecipe.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(MealRecipe.class))).thenReturn(dto);
        assertEquals(MEAL_NAME, service.update(dto).getMeal().getMeal());
    }

    @Test
    @DisplayName("Поиск MealRecipe entity по id")
    void findById() throws EntityNotFoundException {
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(MealRecipe.class))).thenReturn(dto);
        MealRecipeDto resultDto = service.findById(ID);
        assertEquals(ID,resultDto.getId());
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