package ru.otus.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto extends AbstractDto {
    private String name;
    private Map<Long,Integer> nutritionalIdsndAmountMap;
}
