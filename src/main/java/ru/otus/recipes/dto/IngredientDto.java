package ru.otus.recipes.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto extends AbstractDto {
    private String name;
    private List<IngredientNutritionalDto> nutritionalInformation;
}
