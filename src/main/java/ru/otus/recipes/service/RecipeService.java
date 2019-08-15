package ru.otus.recipes.service;

import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe (RecipeDto recipeDto);
    Recipe findRecipeById(long id);
    List<Recipe> findAllRecipes();
    Recipe updateRecipe(RecipeDto recipeDto);
    void deleteRecipeById(long id);
}
