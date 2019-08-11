package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Meal;

public interface MealRepository extends JpaRepository<Meal,Long> {
}
