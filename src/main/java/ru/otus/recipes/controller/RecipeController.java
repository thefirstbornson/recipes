package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.service.RecipeService;


@RestController
@RequestMapping("/recipes")
public class RecipeController extends AbstractController<Recipe, RecipeService, RecipeDto>{
    public RecipeController(RecipeService recipeService) {
        super(recipeService, Recipe.class);
    }
}
