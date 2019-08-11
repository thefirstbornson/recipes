package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

}
