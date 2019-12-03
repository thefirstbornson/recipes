package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.MealDto;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.MenuDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.repository.MenuRepository;
import ru.otus.recipes.service.mapper.MealMapper;
import ru.otus.recipes.service.mapper.MenuMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MenuServiceTest {
    private static final String RECIPE_NAME = "testRecipe";
    private static final String RECIPE_DESCRIPTION = "testDescription";
    private static final String INSTRUCTIONS = "testInstructions";
    private static final Integer COOK_TIME = 30;
    private static final Integer LEVEL_ID = 1;
    private static final Integer CUISINE_ID = 1;
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "testImagePath";
    private static final Long ID = 1L;
    private static final Long ID_UPDATE = 2L;
    private static final Long DTO_ID = 1L;
    private static final Long DTO_ID_UPDATE = 2L;
    private Menu persistedEntity;
    private MenuDto dto;

    @MockBean
    private MenuRepository repository;
    @MockBean
    private MealRecipeService mealRecipeService;
    @MockBean
    private MenuMapper mapper;
    private MenuService service;

    @BeforeEach
    void setUp() {
        service = new MenuService(repository, mapper,mealRecipeService);
        MealRecipe mealRecipe = new MealRecipe();
        mealRecipe.setId(ID);
        RecipeDto recipeDto =
                new RecipeDto(1, RECIPE_NAME, RECIPE_DESCRIPTION, INSTRUCTIONS, COOK_TIME, LEVEL_ID, CUISINE_ID, RATING, IMAGE_PATH,
                        new HashMap<>(), Arrays.asList(1L, 2L), Arrays.asList(1L, 2L), Arrays.asList(1L, 2L));
        MealRecipeDto mealRecipeDto = new MealRecipeDto(DTO_ID, new HashSet<>(List.of(recipeDto)),null);
        persistedEntity = new Menu(ID, new HashSet<>(List.of(mealRecipe)));
        dto = new MenuDto(new HashSet<>(List.of(mealRecipeDto)));
        dto.setId(DTO_ID);
        Menu menu = new Menu();
        Mockito.when(mapper.toEntity(any(MenuDto.class))).thenReturn(menu);
    }

    @Test
    @DisplayName("Сохранение  Meal entity")
    void save() throws EntityExistsException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(Menu.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Menu.class))).thenReturn(dto);
        assertEquals(DTO_ID, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление meal entity")
    void update() throws EntityNotFoundException {
        MealRecipeDto mealRecipeDto = new MealRecipeDto();
        mealRecipeDto.setId(ID_UPDATE);
        dto.setMealRecipes(new HashSet<>(List.of(mealRecipeDto)));
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Menu.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Menu.class))).thenReturn(dto);
        assertEquals(ID_UPDATE, service.update(dto).getMealRecipes().stream().findFirst().get().getId());
    }

    @Test
    @DisplayName("Поиск Meal entity по id")
    void findById() throws EntityNotFoundException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Menu.class))).thenReturn(dto);
        MenuDto resultDto = service.findById(ID);
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