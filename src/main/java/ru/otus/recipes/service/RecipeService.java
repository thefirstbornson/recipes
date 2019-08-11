package ru.otus.recipes.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;

public interface RecipeService {
    Recipe createRecipe (RecipeDto recipeDto);
    Recipe getRecipe(long id);
}
