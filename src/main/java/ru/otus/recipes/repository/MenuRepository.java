package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.recipes.domain.Menu;

@Repository
public interface MenuRepository extends CommonRepository<Menu> {
//    @Query(value = "DELETE FROM mng_mealreciperecipes WHERE mealrecipe_id =:mealRecipeId",
//            nativeQuery = true)
//    void deleteAllMealRecipes(Long mealRecipeId);
//
//    @Query(value = "DELETE FROM mng_mealreciperecipes",
//            nativeQuery = true)
//    void deleteAllMealRecipes();
}

