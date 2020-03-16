package ru.otus.recipes.service.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.*;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.*;
import ru.otus.recipes.service.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeMapper extends AbstractMapper<RecipeDto, Recipe> {
    private final LevelService levelService;
    private final CuisineService cuisineService;
    private final FoodCategoryService foodCategoryService;
    private final MealService mealService;
    private final CourseService courseService;
    private final ModelMapper mapper;
    private final LevelMapper levelMapper;
    private final CuisineMapper cuisineMapper;
    private final IngredientMapper ingredientMapper;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final CourseMapper courseMapper;
    private final FoodCategoryMapper foodCategoryMapper;
    private final MealMapper mealMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;
    @Autowired
    RecipeMapper(LevelService levelService,
                 CuisineService cuisineService, FoodCategoryService foodCategoryService, MealService mealService,
                 CourseService courseService, ModelMapper mapper, LevelMapper levelMapper, CuisineMapper cuisineMapper, IngredientMapper ingredientMapper,
                 RecipeIngredientRepository recipeIngredientRepository, CourseMapper courseMapper, FoodCategoryMapper foodCategoryMapper, MealMapper mealMapper, RecipeIngredientMapper recipeIngredientMapper) {
        super(Recipe.class, RecipeDto.class);
        this.levelService = levelService;
        this.cuisineService = cuisineService;
        this.foodCategoryService = foodCategoryService;
        this.mealService = mealService;
        this.courseService = courseService;
        this.mapper = mapper;
        this.levelMapper = levelMapper;
        this.cuisineMapper = cuisineMapper;
        this.ingredientMapper = ingredientMapper;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.courseMapper = courseMapper;
        this.foodCategoryMapper = foodCategoryMapper;
        this.mealMapper = mealMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Recipe.class, RecipeDto.class)
                .addMappings(m -> m.skip(RecipeDto::setLevel))
                .addMappings(m -> m.skip(RecipeDto::setCuisine))
                .addMappings(m -> m.skip(RecipeDto::setCourseList))
                .addMappings(m -> m.skip(RecipeDto::setFoodCategoryList))
                .addMappings(m -> m.skip(RecipeDto::setMealList))
                .addMappings(m -> m.skip(RecipeDto::setIngredientsInfo))
                .setPostConverter(converter);
        mapper.createTypeMap(RecipeDto.class, Recipe.class)
                .addMappings(m -> m.skip(Recipe::setCuisine))
                .addMappings(m -> m.skip(Recipe::setCourses))
                .addMappings(m -> m.skip(Recipe::setFoodCategories))
                .addMappings(m -> m.skip(Recipe::setMeals))
                .addMappings(m -> m.skip(Recipe::setRecipeIngredients))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Recipe source, RecipeDto destination) {
//         (true) ? destination.setLevel(levelMapper.toDto(source.getLevel())) : destination.setLevel()); ;
//        source = (Recipe) createProxy(source.getClass());
        destination.setCuisine(cuisineMapper.toDto((Cuisine)returnProxyIfRequired(source.getCuisine())));
        destination.setCourseList(source.getCourses().stream().map(courseMapper::toDto).collect(Collectors.toList()));
        destination.setFoodCategoryList(source.getFoodCategories().stream().map(foodCategoryMapper::toDto).collect(Collectors.toList()));
        destination.setMealList(source.getMeals().stream().map(mealMapper::toDto).collect(Collectors.toList()));
        destination.setIngredientsInfo(source.getRecipeIngredients()
                .stream()
                .map(recipeIngredientMapper::toDto)
                .collect(Collectors.toList()));
    }

    Object returnProxyIfRequired(Cuisine source){
        return 1==1 ? createProxy(source.getClass()) : source;
    }

    @Override
    void mapSpecificFields(RecipeDto source, Recipe destination) throws EntityNotFoundException {
        destination.setLevel(levelService.getEntityById(source.getLevel().getId()));
        destination.setCuisine(cuisineService.getEntityById(source.getCuisine().getId()));
        destination.setCourses(new HashSet<>(courseService.getAllEntitiesByIds(source.getCourseList()
                .stream()
                .map(CourseDto::getId)
                .collect(Collectors.toList()))));
        destination.setFoodCategories(new HashSet<>(foodCategoryService.getAllEntitiesByIds(source.getFoodCategoryList()
                .stream()
                .map(FoodCategoryDto::getId)
                .collect(Collectors.toList()))));
        destination.setMeals(new HashSet<>(mealService.getAllEntitiesByIds(source.getMealList()
                .stream()
                .map(MealDto::getId)
                .collect(Collectors.toList()))));
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findRecipeIngredientListByRecipeId(source.getId());
        destination.setRecipeIngredients(recipeIngredientList);
    }
}
