package ru.otus.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.recipes.domain.MealRecipe;

@Repository
public interface MealRecipeRepository extends CommonRepository<MealRecipe> {

    @Query(value = "DELETE FROM mng_mealreciperecipes WHERE mealrecipe_id = ?1",
            nativeQuery = true)
    void deleteAllMealRecipes(Long mealRecipeId );

    @Query(value = "DELETE FROM mng_mealreciperecipes",
            nativeQuery = true)
    void deleteAllMealRecipes();
}
