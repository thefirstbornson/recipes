package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.service.MealRecipeService;
import ru.otus.recipes.service.RecipeService;


@RestController
@RequestMapping("/meal-recipes")
public class MealRecipeController extends AbstractController<MealRecipe, MealRecipeService, MealRecipeDto>{
    public MealRecipeController(MealRecipeService mealRecipeService) {
        super(mealRecipeService, MealRecipe.class);
    }
}
