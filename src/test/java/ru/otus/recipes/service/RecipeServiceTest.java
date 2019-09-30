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
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.RecipeIngredientRepository;
import ru.otus.recipes.repository.RecipeRepository;
import ru.otus.recipes.service.mapper.RecipeMapper;


import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class RecipeServiceTest {
    private Recipe persistedRecipe;
    private RecipeDto RecipeDto;
    private RecipeDto persistedRecipeDto;

    @MockBean
    private RecipeRepository recipeRepository;
    @MockBean
    private RecipeMapper recipeMapper;
    @MockBean
    private RecipeIngredientRepository recipeIngredientRepository;
    @Bean
    RecipeService RecipeService() {
        return new RecipeService(recipeRepository, recipeIngredientRepository, recipeMapper);
    }

    @BeforeEach
    void setUp() {
        Recipe recipe = new Recipe();
        RecipeDto = new RecipeDto(0,"1","2","3",30,1,1,1,"",
                new HashMap<>(), Arrays.asList(1L,2L),Arrays.asList(1L,2L),Arrays.asList(1L,2L));
        persistedRecipe = new Recipe();
        persistedRecipeDto = new RecipeDto(1,"1","2","3",30,1,1,1,"",
                new HashMap<>(), Arrays.asList(1L,2L),Arrays.asList(1L,2L),Arrays.asList(1L,2L));
        Mockito.when(recipeMapper.toEntity(any(RecipeDto.class))).thenReturn(recipe);
    }

    @Test
    @DisplayName("Saving the Recipe entity")
    void save() {
        persistedRecipeDto.setId(1L);
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(persistedRecipe);
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(persistedRecipeDto);
        assertEquals(1,RecipeService().save(RecipeDto).getId());
    }

    @Test
    @DisplayName("Updating the Recipe entity")
    void update() {
        persistedRecipeDto.setName("newRecipeName");
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(persistedRecipe);
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(persistedRecipeDto);
        assertEquals("newRecipeName",RecipeService().update(RecipeDto).getName());
    }

    @Test
    @DisplayName("Finding the Recipe entity by id")
    void findById() throws EntityNotFoundException {
        persistedRecipeDto.setId(1);
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedRecipe));
        Mockito.when(recipeMapper.toDto(any(Recipe.class))).thenReturn(persistedRecipeDto);
        RecipeDto resultDto = RecipeService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}