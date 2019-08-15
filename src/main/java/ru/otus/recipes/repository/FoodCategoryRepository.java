package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.FoodCategory;

import java.util.List;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory,Long> {
    List<FoodCategory> findByIdIn(List<Long> ids);
}
