package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.*;
import ru.otus.recipes.service.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeMapper extends AbstractMapper<RecipeDto, Recipe> {
    private final IngredientService ingredientService;
    private final LevelService levelService;
    private final CuisineService cuisineService;
    private final FoodCategoryService foodCategoryService;
    private final MealService mealService;
    private final MeasurementRepository measurementRepository;
    private final CourseService courseService;
    private final ModelMapper mapper;
    private final IngredientMapper ingredientMapper;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    RecipeMapper(IngredientService ingredientService, LevelService levelService,
                 CuisineService cuisineService, FoodCategoryService foodCategoryService, MealService mealService,
                 MeasurementRepository measurementRepository, CourseService courseService, ModelMapper mapper, IngredientMapper ingredientMapper, RecipeIngredientRepository recipeIngredientRepository) {
        super(Recipe.class, RecipeDto.class);
        this.ingredientService = ingredientService;
        this.levelService = levelService;
        this.cuisineService = cuisineService;
        this.foodCategoryService = foodCategoryService;
        this.mealService = mealService;
        this.measurementRepository = measurementRepository;
        this.courseService = courseService;
        this.mapper = mapper;
        this.ingredientMapper = ingredientMapper;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Recipe.class, RecipeDto.class)
                .addMappings(m -> m.skip(RecipeDto::setCourseIdList))
                .addMappings(m -> m.skip(RecipeDto::setFoodCategoryIdList))
                .addMappings(m -> m.skip(RecipeDto::setMealIdList))
                .addMappings(m -> m.skip(RecipeDto::setIngredients))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(RecipeDto.class, Recipe.class)
                .addMappings(m -> m.skip(Recipe::setLevel))
                .addMappings(m -> m.skip(Recipe::setCuisine))
                .addMappings(m -> m.skip(Recipe::setCourses))
                .addMappings(m -> m.skip(Recipe::setFoodCategories))
                .addMappings(m -> m.skip(Recipe::setMeals))
                .addMappings(m -> m.skip(Recipe::setRecipeIngredients))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Recipe source, RecipeDto destination) {
        destination.setCourseIdList(source.getCourses().stream().map(Course::getId).collect(Collectors.toList()));
        destination.setFoodCategoryIdList(source.getFoodCategories().stream().map(FoodCategory::getId).collect(Collectors.toList()));
        destination.setMealIdList(source.getMeals().stream().map(Meal::getId).collect(Collectors.toList()));
//        Map<String,Map<String,Long>> ingredientIdAndAmountMeasurementMap = new HashMap<>();
//        source.getRecipeIngredients()
//                .forEach(recipeIngr -> {ingredientIdAndAmountMeasurementMap.put(String.valueOf(recipeIngr.getIngredient().getId()),
//                                Map.of("measurement_id", recipeIngr.getMeasurement().getId(),"amount", (long) recipeIngr.getAmount()));
//                });
        destination.setIngredients(source.getRecipeIngredients()
                .stream()
                .map(RecipeIngredient::getIngredient)
                .map(ingredientMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Override
    void mapSpecificFields(RecipeDto source, Recipe destination) throws EntityNotFoundException {
        destination.setLevel(levelService.getEntityById(source.getLevelId()));
        destination.setCuisine(cuisineService.getEntityById(source.getCuisineId()));
        destination.setCourses(new HashSet<>(courseService.getAllEntitiesByIds(source.getCourseIdList())));
        destination.setFoodCategories(new HashSet<>(foodCategoryService.getAllEntitiesByIds(source.getFoodCategoryIdList())));
        destination.setMeals(new HashSet<>(mealService.getAllEntitiesByIds(source.getMealIdList())));
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findRecipeIngredientListByRecipeId(source.getId());
//                extractRecipeIngredientListFromMap(source.getIngredientIdAndMeasurementIdAmountMap(),destination);
        destination.setRecipeIngredients(recipeIngredientList);
    }

//    private List<RecipeIngredient> extractRecipeIngredientListFromMap(Map<String, Map<String ,Long>> ingredinentMeasureAmountMap,
//                                                                      Recipe recipe) {
//        return ingredinentMeasureAmountMap.keySet()
//                .stream()
//                .map(ingredientId ->{
//                    RecipeIngredient recipeIngredient =  new RecipeIngredient();
//                    recipeIngredient.setRecipe(recipe);
//                    recipeIngredient.setIngredient(ingredientService.getEntityById(Long.parseLong(ingredientId)));
//                    recipeIngredient.setAmount(toIntExact(ingredinentMeasureAmountMap.get(ingredientId).get("amount")));
//                    recipeIngredient.setMeasurement(measurementRepository.getOne(
//                            ingredinentMeasureAmountMap.get(ingredientId).get("measurement_id")));
//                    return recipeIngredient;})
//                .collect(Collectors.toList());
//    }
}
