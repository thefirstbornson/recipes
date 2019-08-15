package ru.otus.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class RecipeDto {
    private long id;
    private String name;
    private String description;
    private String instructions;
    private int cooktime;
    private long levelId;
    private long cuisineId;
    private int rating;
    private String imagepath;
    private Map<String, Map<String,String>> ingredientIdAndAmountMeasurementMap;
    private List<Long> courseIdList;
    private List<Long> foodCategoryIdList;
    private List<Long> mealIdList;
}
