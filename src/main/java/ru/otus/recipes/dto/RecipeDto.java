package ru.otus.recipes.dto;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class RecipeDto extends AbstractDto {
    private long id;
    private String name;
    private String description;
    private String instructions;
    private int cooktime;
    private LevelDto level;
    private CuisineDto cuisine;
    private int rating;
    private String imagepath;
//    private Map<String, Map<String,Long>> ingredientIdAndMeasurementIdAmountMap;
    private List<RecipeIngredientDto> ingredientsInfo;
    private List<CourseDto> courseList;
    private List<FoodCategoryDto> foodCategoryList;
    private List<MealDto> mealList;

    public RecipeDto(long id, String name, String description, String instructions, int cooktime, LevelDto level,
                     CuisineDto cuisine, int rating, String imagepath, List<RecipeIngredientDto> ingredientsInfo,
                     List<CourseDto> courseList, List<FoodCategoryDto> foodCategoryList, List<MealDto> mealList) {
        super();
        this.id=id;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.cooktime = cooktime;
        this.level = level;
        this.cuisine = cuisine;
        this.rating = rating;
        this.imagepath = imagepath;
        this.ingredientsInfo = ingredientsInfo;
        this.courseList = courseList;
        this.foodCategoryList = foodCategoryList;
        this.mealList = mealList;
    }
}

