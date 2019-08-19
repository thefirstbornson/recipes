package ru.otus.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class IngredientDto extends AbstractDto {
    private long id;
    private String name;
    private HashMap<String,String> nutritionalIdsndAmountMap;
}
