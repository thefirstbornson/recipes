package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConversionDtoServiceImpl implements ConversionDtoServcie {
    @Override
    public RecipeDto convertToDto(Recipe recipe) {
         long id = recipe.getId();
         String name = recipe.getName();
         String description = recipe.getDescription();
         String instructions = recipe.getInstructions();
         int cooktime = recipe.getCooktime();
         long levelId = recipe.getLevel().getId();
         long cuisineId = recipe.getCuisine().getId();
         int rating = recipe.getRating();
         String imagepath=recipe.getImagepath();
         List<Long> courseIdList = recipe.getCourses().stream().map(Course::getId).collect(Collectors.toList());
         List<Long> foodCategoryIdList = recipe.getFoodCategories().stream().map(FoodCategory::getId).collect(Collectors.toList());
         List<Long> mealIdList= recipe.getMeals().stream().map(Meal::getId).collect(Collectors.toList());
         Map<String,Map<String,String>> ingredientIdAndAmountMeasurementMap = new HashMap<>();
         recipe.getRecipeIngredients()
                .forEach(recipeIngr -> ingredientIdAndAmountMeasurementMap.
                        put( String.valueOf(recipeIngr.getIngredient().getId()),
                                Stream.of(new String[][] {{ "measurement_id", String.valueOf(recipeIngr.getMeasurement().getId())},
                                        {"amount", String.valueOf(recipeIngr.getAmount())},})
                                        .collect(Collectors.toMap(data -> data[0], data -> data[1]))));
        return new RecipeDto(id, name, description, instructions, cooktime, levelId, cuisineId, rating, imagepath,
                ingredientIdAndAmountMeasurementMap, courseIdList, foodCategoryIdList, mealIdList) ;
    }
}
