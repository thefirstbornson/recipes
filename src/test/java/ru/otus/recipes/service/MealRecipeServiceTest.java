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
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.MealDto;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRecipeRepository;
import ru.otus.recipes.repository.RecipeIngredientRepository;
import ru.otus.recipes.repository.RecipeRepository;
import ru.otus.recipes.service.mapper.MealRecipeMapper;
import ru.otus.recipes.service.mapper.RecipeMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MealRecipeServiceTest {
    private static final String RECIPE_NAME = "testRecipe";
    private static final String RECIPE_DESCRIPTION = "testDescription";
    private static final String INSTRUCTIONS = "testInstructions";
    private static final Integer COOK_TIME = 30;
    private static final Integer LEVEL_ID = 1;
    private static final Integer CUISINE_ID = 1;
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "testImagePath";
    private static final String MEAL_NAME = "testMealName";
    private static final Long MEAL_RECIPE_ID = 1L;
    private static final Long MEAL_RECIPE_DTO_ID = 1L;
    private static final Long MEAL_RECIPE_DTO_ID_UPDATE = 2L;
    private MealRecipe persistedMealRecipe;
    private MealRecipeDto persistedMealRecipeDto;
    private MealRecipeDto mealRecipeDto;
    private MealDto mealDto;
    private MealRecipeService mealRecipeService;

    @MockBean
    private MealRecipeRepository mealRecipeRepository;
    @MockBean
    private MealRecipeMapper mealRecipeMapper;

    @BeforeEach
    void setUp() {
        mealRecipeService = new MealRecipeService(mealRecipeRepository,  mealRecipeMapper);
        mealDto = new MealDto(MEAL_NAME);
        RecipeDto recipeDto = new RecipeDto(1, RECIPE_NAME, RECIPE_DESCRIPTION, INSTRUCTIONS, COOK_TIME, LEVEL_ID, CUISINE_ID, RATING, IMAGE_PATH,
                new HashMap<>(), Arrays.asList(1L, 2L), Arrays.asList(1L, 2L), Arrays.asList(1L, 2L));
        mealRecipeDto = new MealRecipeDto(MEAL_RECIPE_DTO_ID, new HashSet<>(List.of(recipeDto)),null);
        persistedMealRecipeDto = new MealRecipeDto(MEAL_RECIPE_DTO_ID, new HashSet<>(List.of(recipeDto)),null);
        persistedMealRecipeDto.setId(MEAL_RECIPE_ID);
        persistedMealRecipe = new MealRecipe();
        persistedMealRecipe.setId(MEAL_RECIPE_ID);
        MealRecipe mealRecipe = new MealRecipe();
        Mockito.when(mealRecipeMapper.toEntity(any(MealRecipeDto.class))).thenReturn(mealRecipe);
    }

    @Test
    @DisplayName("Saving the MealRecipe entity")
    void save() throws EntityExistsException {
        Mockito.when(mealRecipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(mealRecipeMapper.toDto(any(MealRecipe.class))).thenReturn(persistedMealRecipeDto);
        Mockito.when(mealRecipeRepository.save(any(MealRecipe.class))).thenReturn(persistedMealRecipe);
        assertEquals(MEAL_RECIPE_ID,mealRecipeService.save(mealRecipeDto).getId());
    }

    @Test
    @DisplayName("Updating the MealRecipe entity")
    void update() throws EntityNotFoundException {
        persistedMealRecipeDto.setMealId(MEAL_RECIPE_DTO_ID_UPDATE);
        Mockito.when(mealRecipeRepository.findById(anyLong())).thenReturn(Optional.of(persistedMealRecipe));
        Mockito.when(mealRecipeRepository.save(any(MealRecipe.class))).thenReturn(persistedMealRecipe);
        Mockito.when(mealRecipeMapper.toDto(any(MealRecipe.class))).thenReturn(persistedMealRecipeDto);
        assertEquals(MEAL_RECIPE_DTO_ID_UPDATE,mealRecipeService.update(mealRecipeDto).getMealId());
    }

    @Test
    @DisplayName("Finding the MealRecipe entity by id")
    void findById() throws EntityNotFoundException {
        Mockito.when(mealRecipeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedMealRecipe));
        Mockito.when(mealRecipeMapper.toDto(any(MealRecipe.class))).thenReturn(persistedMealRecipeDto);
        MealRecipeDto resultDto = mealRecipeService.findById(MEAL_RECIPE_ID);
        assertEquals(MEAL_RECIPE_ID,resultDto.getId());
    }
}