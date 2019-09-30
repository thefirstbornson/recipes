package ru.otus.recipes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RecipeIngredientDto extends AbstractDto{
    private IngredientDto ingredient;
    private int amount;
    private MeasurementDto measurement;
}
