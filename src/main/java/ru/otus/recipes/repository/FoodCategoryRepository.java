package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory,Long> {
}
