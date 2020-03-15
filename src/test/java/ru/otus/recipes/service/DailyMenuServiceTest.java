package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.DailyMenu;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.*;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.DailyMenuRepository;
import ru.otus.recipes.service.mapper.DailyMenuMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class DailyMenuServiceTest {
    private static final String RECIPE_NAME = "testRecipe";
    private static final String RECIPE_DESCRIPTION = "testDescription";
    private static final String INSTRUCTIONS = "testInstructions";
    private static final Integer COOK_TIME = 30;
    private static final LevelDto LEVEL_DTO = new LevelDto(1);
    private static final CuisineDto CUISINE_DTO = new CuisineDto("testCuisine");
    public static final MealDto MEAL_DTO = new MealDto("testMeal");
    private static final Integer RATING = 1;
    private static final String IMAGE_PATH = "testImagePath";
    private static final Long ID = 1L;
    private static final Long ID_UPDATE = 2L;
    private static final Long DTO_ID = 1L;
    private MenuDto menuDtoUpdated;
    private DailyMenu persistedEntity;
    private DailyMenuDto dto;

    @MockBean
    private DailyMenuRepository repository;
    @MockBean
    private DailyMenuService dailyMenuService;
    @MockBean
    private DailyMenuMapper mapper;
    private DailyMenuService service;

    @BeforeEach
    void setUp() {
        service = new DailyMenuService(repository, mapper);
        NutritionalInformationDto nutritionalInformationDto = new NutritionalInformationDto("testNutrition");
        IngredientNutritionalDto ingredientNutritionalDto = new IngredientNutritionalDto(nutritionalInformationDto,1);
        IngredientDto ingredientDto= new IngredientDto("testIngredient", Collections.singletonList(ingredientNutritionalDto));
        MeasurementDto measurementDto = new MeasurementDto("testMeasurement");
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(ingredientDto,1,measurementDto);
        FoodCategoryDto foodCategoryDto = new FoodCategoryDto("testFoodCategory");
        CourseDto courseDto = new CourseDto("testCourse");
        MealDto mealDto = new MealDto("testMeal");
        MealRecipe mealRecipe = new MealRecipe();
        mealRecipe.setId(ID);
        RecipeDto recipeDto =
                new RecipeDto(0,RECIPE_NAME,RECIPE_DESCRIPTION,INSTRUCTIONS,COOK_TIME, LEVEL_DTO, CUISINE_DTO,RATING,IMAGE_PATH,
                        Collections.singletonList(recipeIngredientDto), Collections.singletonList(courseDto), Collections.singletonList(foodCategoryDto),
                        Collections.singletonList(mealDto));
        MealRecipeDto mealRecipeDto = new MealRecipeDto(MEAL_DTO , new HashSet<>(List.of(recipeDto)));
        Menu menu = new Menu(ID, List.of(mealRecipe));
        persistedEntity = new DailyMenu(ID,Calendar.getInstance().getTime(),menu);
        MenuDto menuDto = new MenuDto(Collections.singletonList(mealRecipeDto));
        menuDtoUpdated = new MenuDto(Collections.emptyList());
        dto = new DailyMenuDto(Calendar.getInstance().getTime(),menuDto);
        dto.setId(1L);

        DailyMenu dailyMenu =  new DailyMenu(ID,Calendar.getInstance().getTime(),menu);
        Mockito.when(mapper.toEntity(any(DailyMenuDto.class))).thenReturn(dailyMenu);
    }

    @Test
    @DisplayName("Сохранение  Meal entity")
    void save() throws EntityExistsException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(DailyMenu.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(DailyMenu.class))).thenReturn(dto);
        assertEquals(DTO_ID, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление meal entity")
    void update() throws EntityNotFoundException {
        dto.setMenu(menuDtoUpdated);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(DailyMenu.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(DailyMenu.class))).thenReturn(dto);
        assertTrue(service.update(dto).getMenu().getMealRecipes().isEmpty());
    }

    @Test
    @DisplayName("Поиск Meal entity по id")
    void findById() throws EntityNotFoundException {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(DailyMenu.class))).thenReturn(dto);
        DailyMenuDto resultDto = service.findById(ID);
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