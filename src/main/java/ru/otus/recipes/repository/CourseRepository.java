package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.domain.Ingredient;

import java.util.List;

@Repository
public interface CourseRepository extends CommonRepository<Course> {
}
