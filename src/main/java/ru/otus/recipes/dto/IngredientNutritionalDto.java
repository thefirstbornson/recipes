package ru.otus.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientNutritionalDto  extends AbstractDto  {
    private NutritionalInformationDto nutrition;
    private float amount;
}
