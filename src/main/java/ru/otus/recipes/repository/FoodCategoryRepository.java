package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.domain.FoodCategory;

import java.util.List;

public interface FoodCategoryRepository  extends CommonRepository<FoodCategory>{
}
