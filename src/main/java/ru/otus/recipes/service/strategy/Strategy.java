package ru.otus.recipes.service.strategy;

import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.domain.Recipe;

import java.util.List;

public interface Strategy {
    Menu createMenu();
    List<Meal> getMenuMeals();
    void setMenuMeals(List<Meal> meals);
    Recipe getRecipe();
    MealRecipe getMealRecipe(Meal meal);
}
