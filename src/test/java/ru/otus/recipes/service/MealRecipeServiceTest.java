package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
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
    private static final Integer LEVEL_ID = 1;
    private static final Integer CUISINE_ID = 1;
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "testImagePath";
    private static final String MEAL_NAME = "testMealName";
    private static final Long ID = 1L;
    private static final Long DTO_ID = 1L;
    private static final Long DTO_ID_UPDATE = 2L;
    private MealRecipe persistedEntity;
    private MealRecipeDto dto;
    private MealRecipeDto mealRecipeDto;
    private MealRecipeService service;

    @MockBean
    private MealRecipeRepository repository;
    @MockBean
    private MealRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        service = new MealRecipeService(repository, mapper);
        RecipeDto recipeDto = new RecipeDto(1, RECIPE_NAME, RECIPE_DESCRIPTION, INSTRUCTIONS, COOK_TIME, LEVEL_ID, CUISINE_ID, RATING, IMAGE_PATH,
                new HashMap<>(), Arrays.asList(1L, 2L), Arrays.asList(1L, 2L), Arrays.asList(1L, 2L));
        mealRecipeDto = new MealRecipeDto(DTO_ID, new HashSet<>(List.of(recipeDto)),null);
        dto = new MealRecipeDto(DTO_ID, new HashSet<>(List.of(recipeDto)),null);
        dto.setId(ID);
        persistedEntity = new MealRecipe();
        persistedEntity.setId(ID);
        MealRecipe mealRecipe = new MealRecipe();
        Mockito.when(mapper.toEntity(any(MealRecipeDto.class))).thenReturn(mealRecipe);
    }

    @Test
    @DisplayName("Saving the MealRecipe entity")
    void save() throws EntityExistsException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(mapper.toDto(any(MealRecipe.class))).thenReturn(dto);
        Mockito.when(repository.save(any(MealRecipe.class))).thenReturn(persistedEntity);
        assertEquals(ID, service.save(mealRecipeDto).getId());
    }

    @Test
    @DisplayName("Updating the MealRecipe entity")
    void update() throws EntityNotFoundException {
        dto.setMealId(DTO_ID_UPDATE);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(MealRecipe.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(MealRecipe.class))).thenReturn(dto);
        assertEquals(DTO_ID_UPDATE, service.update(mealRecipeDto).getMealId());
    }

    @Test
    @DisplayName("Finding the MealRecipe entity by id")
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
}