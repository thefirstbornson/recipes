package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.RecipeIngredientRepository;
import ru.otus.recipes.repository.RecipeRepository;
import ru.otus.recipes.service.mapper.RecipeMapper;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static final Integer LEVEL_ID = 1;
    private static final Integer CUISINE_ID = 1;
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "testImagePath";
    private Recipe persistedRecipe;
    private RecipeDto recipeDto;
    private RecipeDto persistedRecipeDto;

    @MockBean
    private RecipeRepository recipeRepository;
    @MockBean
    private RecipeMapper recipeMapper;
    @MockBean
    private RecipeIngredientRepository recipeIngredientRepository;
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeService(recipeRepository, recipeIngredientRepository, recipeMapper);
        Recipe recipe = new Recipe();
        recipeDto = new RecipeDto(0,RECIPE_NAME,RECIPE_DESCRIPTION,INSTRUCTIONS,COOK_TIME,LEVEL_ID,CUISINE_ID,RATING,IMAGE_PATH,
                new HashMap<>(), Arrays.asList(1L,2L),Arrays.asList(1L,2L),Arrays.asList(1L,2L));
        persistedRecipe = new Recipe();
        persistedRecipeDto = new RecipeDto(DTO_ID,RECIPE_NAME,RECIPE_DESCRIPTION,INSTRUCTIONS,COOK_TIME,LEVEL_ID,CUISINE_ID,RATING,IMAGE_PATH,
                new HashMap<>(), Arrays.asList(1L,2L),Arrays.asList(1L,2L),Arrays.asList(1L,2L));
        Mockito.when(recipeMapper.toEntity(any(RecipeDto.class))).thenReturn(recipe);
    }

    @Test
    @DisplayName("Saving the Recipe entity")
    void save() throws EntityExistsException {
        persistedRecipeDto.setId(DTO_ID);
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(persistedRecipe);
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(persistedRecipeDto);
        assertEquals(DTO_ID,recipeService.save(recipeDto).getId());
    }

    @Test
    @DisplayName("Updating the Recipe entity")
    void update() throws EntityNotFoundException {
        persistedRecipeDto.setName("newRecipeName");
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(persistedRecipe));
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(persistedRecipe);
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(persistedRecipeDto);
        assertEquals("newRecipeName",recipeService.update(recipeDto).getName());
    }

    @Test
    @DisplayName("Finding the Recipe entity by id")
    void findById() throws EntityNotFoundException {
        persistedRecipeDto.setId(1);
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedRecipe));
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(persistedRecipeDto);
        RecipeDto resultDto = recipeService.findById(1L);
        assertEquals(DTO_ID,resultDto.getId());
    }
}