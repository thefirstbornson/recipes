package ru.otus.recipes.service;


import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.repository.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final LevelRepository levelRepository;
    private final CourseRepository courseRepository;
    private final CuisineRepository cuisineRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MealRepository mealRepository;
    private final MeasurementRepository measurementRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository,
                             IngredientService ingredientService, LevelRepository levelRepository, CourseRepository courseRepository,
                             CuisineRepository cuisineRepository,
                             FoodCategoryRepository foodCategoryRepository, MealRepository mealRepository,
                             MeasurementRepository measurementRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.levelRepository = levelRepository;
        this.courseRepository = courseRepository;
        this.cuisineRepository = cuisineRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.mealRepository = mealRepository;
        this.measurementRepository = measurementRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;

    }

    @Override
    @Transactional
    public Recipe createRecipe(RecipeDto recipeDto) throws EmptyResultDataAccessException {
        Level level = levelRepository.getOne(recipeDto.getLevelId());
        Cuisine cuisine=cuisineRepository.getOne(recipeDto.getCuisineId());
        List<Course> courseList = courseRepository.findByIdIn(recipeDto.getCourseIdList());
        List<FoodCategory> foodCategoryList = foodCategoryRepository.findByIdIn(recipeDto.getFoodCategoryIdList());
        List<Meal> mealList = mealRepository.findByIdIn(recipeDto.getMealIdList());
        List<RecipeIngredient> recipeIngredientList = extractRecipeIngredientListFromMap(recipeDto.getIngredientIdAndAmountMeasurementMap());
        Recipe recipe = new Recipe(recipeDto.getId(), recipeDto.getName(), recipeDto.getDescription(), recipeDto.getInstructions(), recipeDto.getCooktime(),
                recipeDto.getRating(), recipeDto.getImagepath(), level,cuisine, new HashSet<>(courseList), new HashSet<>(foodCategoryList),
                new HashSet<>(mealList),recipeIngredientList);
        return recipeRepository.save(recipe);
    }

    private List<RecipeIngredient> extractRecipeIngredientListFromMap(Map<String,Map<String ,String>> ingredinentMeasureAmountMap) {
        List<Ingredient> ingredientList = ingredientService.findAllIngredients(
                ingredinentMeasureAmountMap
                .keySet()
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList())
        );
        List<Measurement> measurementList = measurementRepository.findByIdIn(
                ingredinentMeasureAmountMap
                .values()
                .stream()
                .map(m->Long.parseLong(m.get("measurement_id")))
                .collect(Collectors.toList())
        );
        return ingredientList.stream()
                .map(ingredient -> new RecipeIngredient(
                        ingredient,
                        Integer.parseInt(
                                ingredinentMeasureAmountMap
                                .get(String.valueOf(ingredient.getId())).get("amount")
                                ),
                        measurementList.stream()
                                .filter(m->Long.parseLong(ingredinentMeasureAmountMap
                                        .get(String.valueOf(ingredient.getId())).get("measurement_id"))==m.getId())
                                .findAny()
                                .orElseThrow(()->new EmptyResultDataAccessException(
                                        String.format("No %s entity exists!",
                                                Measurement.class.getTypeName()), 1
                                        )
                                )
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public Recipe updateRecipe(RecipeDto recipeDto) {
        return createRecipe(recipeDto);
    }

    @Override
    @Transactional
    public void deleteRecipeById(long id) throws EmptyResultDataAccessException {
        recipeIngredientRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe findRecipeById(long id) throws EmptyResultDataAccessException{
        return recipeRepository.findById(id)
                .orElseThrow(()->new EmptyResultDataAccessException(
                        String.format("No %s entity with id %s exists!",Recipe.class.getTypeName(), (int)id), 1)
                );
    }

    @Override
    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }
}
