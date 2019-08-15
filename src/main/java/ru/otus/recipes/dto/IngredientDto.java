package ru.otus.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
@AllArgsConstructor
public class IngredientDto {
    long id;
    String name;
    private HashMap<String,String> nutritionalIdsndAmountMap;
}
