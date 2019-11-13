package ru.otus.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.recipes.domain.MealRecipe;

@Repository
public interface MealRecipeRepository extends JpaRepository<MealRecipe, Long> {
}
