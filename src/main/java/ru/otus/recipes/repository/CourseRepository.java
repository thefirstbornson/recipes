package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.domain.Ingredient;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByIdIn(List<Long> ids);
}
