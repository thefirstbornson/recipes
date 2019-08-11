package ru.otus.recipes.dto;

import java.util.List;

public class RecipeDto {
    private long id;
    private String name;
    private String description;
    private String instructions;
    private int cooktime;
    private long level_id;
    private long cousine_id;
    private long rating;
    private String imagepath;
    private List<Long> ingredientIds;
    private List<Long> courseIds;
    private List<Long> foodCategoryIds;
    private List<Long> mealIds;
}
