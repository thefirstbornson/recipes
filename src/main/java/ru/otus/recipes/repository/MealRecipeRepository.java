package ru.otus.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.MealRecipe;

@Repository
@Transactional
public interface MealRecipeRepository extends CommonRepository<MealRecipe> {
    @Modifying
    @Query(value = "DELETE FROM mng_mealreciperecipes WHERE mealrecipe_id = ?1", nativeQuery = true)
    int deleteAllMealRecipes(Long mealRecipeId );

    @Modifying
    @Query(value = "DELETE FROM mng_mealreciperecipes",nativeQuery = true)
    int deleteAllMealRecipes();

    @Modifying
    @Query("update MealRecipe mr set mr.menu = null where mr.menu.id = :menuId")
    int deleteMenuFromMealRecipes(@Param("menuId") Long menuId);

    @Modifying
    @Query("update MealRecipe mr set mr.menu = null")
    int deleteAllMenusFromMealRecipes();

}
