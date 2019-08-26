package ru.otus.recipes.dto;

import lombok.*;

import java.util.HashMap;
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
    private Map<String, Map<String,String>> ingredientIdAndAmountMeasurementMap;
    private List<Long> courseIdList;
    private List<Long> foodCategoryIdList;
    private List<Long> mealIdList;
}
