package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.domain.RecipeIngredient;

public interface RecipeIngredientRepository  extends  CommonRepository<RecipeIngredient>{
    void deleteByRecipeId (long id);
}
