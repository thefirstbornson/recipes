package ru.otus.recipes.repository;

import ru.otus.recipes.domain.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findByIdIn(List<Long> ids);
}
