package ru.otus.recipes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecipeIngredientDto extends AbstractDto{
    private IngredientDto ingredient;
    private int amount;
    private MeasurementDto measurement;
}
