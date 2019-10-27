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
    private RecipeDto recipeDto;
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
    @Autowired
    MeasurementRepository measurementRepository;

    @BeforeEach
    void setUp()
    {
        recipe = new Recipe();
        recipe.setName("");
        recipe.setDescription("");
        recipe.setInstructions("");
        recipe.setCooktime(30);
        recipe.setImagepath("");
        recipe.setLevel(levelRepository.findById(1L).get());
        recipe.setCuisine(cuisineRepository.findById(1L).get());
        recipe.setCourses(new HashSet<>(Collections.singletonList(courseRepository.findById(1L).get())));
        recipe.setFoodCategories(new HashSet<>(Collections.singletonList(foodCategoryRepository.findById(1L).get())));
        recipe.setMeals(new HashSet<>(Collections.singletonList(mealRepository.findById(1L).get())));
        Ingredient ingredient = ingredientRepository.findById(1L).get();
        Measurement measurement = measurementRepository.findById(1L).get();
        List<RecipeIngredient> recipeIngredientList = Collections.singletonList(new RecipeIngredient(ingredient,10, measurement));
        recipeIngredientList.forEach(recipeIngredient -> recipeIngredient.setRecipe(recipe));
        recipe.setRecipeIngredients(new HashSet<>(recipeIngredientList));

        Map<String,Map<String,Long>> ingredients = new HashMap<>();
        ingredients.put(String.valueOf(1),  new HashMap<>(){{
            put( "measurement_id", 1L );
            put("amount", 10L);
        }});
        recipeDto = new RecipeDto(0,"","","",30,1,1,1,"",
                ingredients, Collections.singletonList(1L), Collections.singletonList(1L), Collections.singletonList(1L));
    }

    @Autowired
    private RecipeMapper recipeMapper;

    @Test
    void toDto() {
        RecipeDto recipeDtoFromEntity = recipeMapper.toDto(recipe);
        assertEquals(recipeDtoFromEntity.getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"),
                recipe.getRecipeIngredients().stream().findFirst().get().getAmount());
    }

    @Test
    void toEntity() {
        Recipe recipeFromDto = recipeMapper.toEntity(recipeDto);
        assertEquals(recipeFromDto.getRecipeIngredients().stream().findFirst().get().getAmount(),
                recipeDto.getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"));
    }
}