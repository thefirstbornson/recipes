package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findByIdIn(List<Long> ids);
}
