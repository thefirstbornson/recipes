package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.domain.Meal;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal,Long> {
    List<Meal> findByIdIn(List<Long> ids);
}
