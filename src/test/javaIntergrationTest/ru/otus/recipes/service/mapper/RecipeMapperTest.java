package ru.otus.recipes.service.mapper;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.repository.*;



import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class RecipeMapperTest {
    private final Map<String, Map<String,Long>> ingredients = new HashMap<>();
    private  RecipeDto recipeDto;
    private Recipe recipe;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    CuisineRepository cuisineRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    FoodCategoryRepository foodCategoryRepository;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    RecipeIngredientRepository recipeIngredientRepository;

    @BeforeEach
    void setUp()
    {
        recipe = new Recipe();
        recipe.setLevel(levelRepository.findById(1L).get());
        recipe.setCuisine(cuisineRepository.findById(1L).get());
        recipe.setCourses(new HashSet<>(Collections.singletonList(courseRepository.findById(1L).get())));
        recipe.setFoodCategories(new HashSet<>(Collections.singletonList(foodCategoryRepository.findById(1L).get())));
        recipe.setMeals(new HashSet<>(Collections.singletonList(mealRepository.findById(1L).get())));
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByIdIn(Arrays.asList(1L,2L));
        for(RecipeIngredient  recipeIngredient : recipeIngredientList) {
            recipeIngredient.setRecipe(recipe);
        }
        recipe.setRecipeIngredients(new HashSet<>(recipeIngredientList));
        recipeRepository.save(recipe);
        recipeDto = new RecipeDto(1,"","","",30,1,1,1,"",
                ingredients, Collections.singletonList(0L), Collections.singletonList(0L), Collections.singletonList(0L));
    }

    @Autowired
    private RecipeMapper recipeMapper;

    @Test
    void toDto() {
        recipeDto = recipeMapper.toDto(recipe);
        assertEquals(recipeDto.getId(), recipe.getId());
    }

    @Test
    void toEntity() {
        Recipe recipeFromDto = recipeMapper.toEntity(recipeDto);
        assertEquals(recipeFromDto.getId(), recipe.getId());
    }
}