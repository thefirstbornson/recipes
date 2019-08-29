package ru.otus.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class IngredientDto extends AbstractDto {
    private String name;
    private Map<Long,Integer> nutritionalIdsndAmountMap;
}
