package ru.otus.recipes.service;

import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;

public interface ConversionDtoServcie {
    RecipeDto convertToDto(Recipe recipe);
}
