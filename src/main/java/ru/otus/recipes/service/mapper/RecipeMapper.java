package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.repository.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

@Service
public class RecipeMapper extends AbstractMapper<RecipeDto,Recipe> {
    private final  IngredientRepository ingredientRepository;
    private final LevelRepository levelRepository;
    private final CourseRepository courseRepository;
    private final CuisineRepository cuisineRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MealRepository mealRepository;
    private final MeasurementRepository measurementRepository;
    private final ModelMapper mapper;

    @Autowired
    RecipeMapper(IngredientRepository ingredientRepository, LevelRepository levelRepository, CourseRepository courseRepository,
                 CuisineRepository cuisineRepository, FoodCategoryRepository foodCategoryRepository, MealRepository mealRepository,
                 MeasurementRepository measurementRepository, ModelMapper mapper) {
        super(Recipe.class, RecipeDto.class);
        this.ingredientRepository = ingredientRepository;
        this.levelRepository = levelRepository;
        this.courseRepository = courseRepository;
        this.cuisineRepository = cuisineRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.mealRepository = mealRepository;
        this.measurementRepository = measurementRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Recipe.class, RecipeDto.class)
                .addMappings(m -> m.skip(RecipeDto::setCourseIdList))
                .addMappings(m -> m.skip(RecipeDto::setFoodCategoryIdList))
                .addMappings(m -> m.skip(RecipeDto::setMealIdList))
                .addMappings(m -> m.skip(RecipeDto::setIngredientIdAndMeasurementIdAmountMap))
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
        Map<String,Map<String,Long>> ingredientIdAndAmountMeasurementMap = new HashMap<>();
        source.getRecipeIngredients()
                .forEach(recipeIngr -> ingredientIdAndAmountMeasurementMap.
                        put( String.valueOf(recipeIngr.getIngredient().getId()),
                                new HashMap<>(){{
                                    put( "measurement_id", recipeIngr.getMeasurement().getId() );
                                    put("amount", (long) recipeIngr.getAmount());
                                }}));
        destination.setIngredientIdAndMeasurementIdAmountMap(ingredientIdAndAmountMeasurementMap);
    }

    @Override
    void mapSpecificFields(RecipeDto source, Recipe destination) {
        destination.setLevel( levelRepository.getOne(source.getLevelId()));
        destination.setCuisine(cuisineRepository.getOne(source.getCuisineId()));
        destination.setCourses(new HashSet<>(courseRepository.findByIdIn(source.getCourseIdList())));
        destination.setFoodCategories(new HashSet<>(foodCategoryRepository.findByIdIn(source.getFoodCategoryIdList())));
        destination.setMeals(new HashSet<>(mealRepository.findByIdIn(source.getMealIdList())));
        ingredientRepository.findByIdIn(Arrays.asList(1L,2L));
        List<RecipeIngredient> recipeIngredientList = extractRecipeIngredientListFromMap(source.getIngredientIdAndMeasurementIdAmountMap());
        for(RecipeIngredient  recipeIngredient : recipeIngredientList) {
            recipeIngredient.setRecipe(destination);
        }
        destination.setRecipeIngredients(new HashSet<>(recipeIngredientList));
    }


    private List<RecipeIngredient> extractRecipeIngredientListFromMap(Map<String, Map<String ,Long>> ingredinentMeasureAmountMap) {
        List<Ingredient> ingredientList = ingredientRepository.findByIdIn(new ArrayList<>(ingredinentMeasureAmountMap.keySet()
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toList())));

        List<Measurement> measurementList = measurementRepository.findByIdIn(
                ingredinentMeasureAmountMap
                        .values()
                        .stream()
                        .map(m->m.get("measurement_id"))
                        .collect(Collectors.toList())
        );
        return ingredientList.stream()
                .map(ingredient -> new RecipeIngredient(
                                ingredient,
                                toIntExact(
                                        ingredinentMeasureAmountMap
                                                .get(String.valueOf(ingredient.getId())).get("amount")
                                ),
                                measurementList.stream()
                                        .filter(m->ingredinentMeasureAmountMap
                                                .get(String.valueOf(ingredient.getId())).get("measurement_id")==m.getId())
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
}
