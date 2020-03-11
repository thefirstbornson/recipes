package ru.otus.recipes.repository;


import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.RecipeIngredient;

import java.util.List;

@Transactional
public interface RecipeIngredientRepository  extends  CommonRepository<RecipeIngredient>{
    void deleteByRecipeId (long id);
    void deleteByIngredientId(long id);
    List<RecipeIngredient> findRecipeIngredientListByRecipeId(long id);
}
