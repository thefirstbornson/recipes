package ru.otus.recipes.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

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
    private long levelId;
    private long cuisineId;
    private int rating;
    private String imagepath;
    private Map<String, Map<String,Long>> ingredientIdAndMeasurementIdAmountMap;
    private List<Long> courseIdList;
    private List<Long> foodCategoryIdList;
    private List<Long> mealIdList;

    public RecipeDto(long id,String name, String description, String instructions, int cooktime, long levelId,
                     long cuisineId, int rating, String imagepath, Map<String, Map<String, Long>> ingredientIdAndMeasurementIdAmountMap,
                     List<Long> courseIdList, List<Long> foodCategoryIdList, List<Long> mealIdList) {
        super();
        this.id=id;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.cooktime = cooktime;
        this.levelId = levelId;
        this.cuisineId = cuisineId;
        this.rating = rating;
        this.imagepath = imagepath;
        this.ingredientIdAndMeasurementIdAmountMap = ingredientIdAndMeasurementIdAmountMap;
        this.courseIdList = courseIdList;
        this.foodCategoryIdList = foodCategoryIdList;
        this.mealIdList = mealIdList;
    }
}

