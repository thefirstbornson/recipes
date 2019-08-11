package ru.otus.recipes.service;


import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;


@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final LevelRepository levelRepository;
    private final CourseRepository courseRepository;
    private final CuisineRepository cuisineRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MealRepository mealRepository;
    private final MeasurementRepository measurementRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository, IngredientRepository ingredientRepository, NutritionalInformationRepository nutritionalInformationRepository, LevelRepository levelRepository, CourseRepository courseRepository, CuisineRepository cuisineRepository, FoodCategoryRepository foodCategoryRepository, MealRepository mealRepository, MeasurementRepository measurementRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.nutritionalInformationRepository = nutritionalInformationRepository;
        this.levelRepository = levelRepository;
        this.courseRepository = courseRepository;
        this.cuisineRepository = cuisineRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.mealRepository = mealRepository;
        this.measurementRepository = measurementRepository;
    }

    @Override
    @Transactional
    public Recipe createRecipe(RecipeDto recipeDto) {

//        NutritionalInformation fat =  nutritionalInformationRepository.findById(1L).get();
//        NutritionalInformation protein = nutritionalInformationRepository.findById(2L).get();
//        NutritionalInformation carbohydrate = nutritionalInformationRepository.findById(3L).get();

        Ingredient ingredient1 = ingredientRepository.getOne(1L);
        Ingredient ingredient2 = ingredientRepository.getOne(2L);
        Ingredient ingredient3 = ingredientRepository.getOne(3L);
        Ingredient ingredient4 = ingredientRepository.getOne(4L);

        Level level = levelRepository.getOne(1l);
        Cuisine cuisine=cuisineRepository.getOne(1L);

        Course course = courseRepository.getOne(1L);
        Course course1 = courseRepository.getOne(2L);

        FoodCategory foodCategory=foodCategoryRepository.getOne(1L);
        FoodCategory foodCategory1=foodCategoryRepository.getOne(2L);

        Meal meal = mealRepository.getOne(1L);

        Measurement measurement= measurementRepository.getOne(1L);

        Recipe recipe = new Recipe("рис с тунцом", "быстрое и вкусное блюдо ",
                "1. Сварить рис, 2. Обжарить лук, морковь, консервированный тунец, 3. добавить рис к зажарке",
                20, 1, "",
                level,cuisine, new HashSet<>(Arrays.asList(course,course1)),
                new HashSet<>(Arrays.asList(foodCategory,foodCategory1)),
                new HashSet<>(Arrays.asList(meal)),
                Arrays.asList(new RecipeIngredient(ingredient1,1,measurement), new RecipeIngredient(ingredient2,1,measurement)));


        recipeRepository.save(recipe);
        return null;
    }

    @Override
    public Recipe getRecipe(long id) {
        return recipeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


}
