package ru.otus.recipes.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto extends AbstractDto{
    private IngredientDto ingredient;
    private int amount;
    private MeasurementDto measurement;
}
