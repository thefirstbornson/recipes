package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long> {
}
