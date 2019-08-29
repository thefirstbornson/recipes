package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Recipe;

public interface RecipeRepository extends  CommonRepository<Recipe>{
}
