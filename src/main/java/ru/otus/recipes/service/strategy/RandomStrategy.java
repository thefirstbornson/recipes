package ru.otus.recipes.service.strategy;

import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomStrategy implements Strategy {
    private List<Meal> menuMeals;
    private final RecipeRepository recipeRepository;

    public RandomStrategy(List<Meal> menuMeals, RecipeRepository recipeRepository) {
        this.menuMeals = menuMeals;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Menu createMenu() {
        return addRandomRecipesToMenu(menuMeals);
    }

    private Menu addRandomRecipesToMenu(List<Meal> menuMeals) {
        Menu menu = new Menu();
        List<MealRecipe> mealRecipes = new ArrayList<>();
        for(Meal meal: menuMeals){
            long randomRecipeId = 1 + (long) (Math.random() * (meal.getRecipes().size() - 1));
            Recipe recipe = meal.getRecipes()
                    .stream()
                    .skip(randomRecipeId-1)
                    .findFirst()
                    .orElse(null);
            mealRecipes.add(new MealRecipe(meal, Collections.singletonList(recipe)));
        }
        menu.setMealRecipes(mealRecipes);
        return menu;
    }

    @Override
    public List<Meal> getMenuMeals() {
        return null;
    }

    @Override
    public void setMenuMeals(List<Meal> meals) {
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public MealRecipe getMealRecipe(Meal meal) {
        return null;
    }
}
