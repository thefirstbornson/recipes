package ru.otus.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Cuisine;

public interface CuisineRepository extends CommonRepository<Cuisine> {
}
