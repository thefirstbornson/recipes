package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.Query;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.domain.Recipe;

import java.util.List;

public interface RecipeRepository extends  CommonRepository<Recipe>{
}
