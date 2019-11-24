package ru.otus.recipes.dto;

import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealRecipeDto extends AbstractDto{
    private Long mealId;
    private Set<RecipeDto> recipes;
    private Long menuId;
}
