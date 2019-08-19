package ru.otus.recipes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FoodCategoryDto  extends AbstractDto{
    private String foodCategory;
}
