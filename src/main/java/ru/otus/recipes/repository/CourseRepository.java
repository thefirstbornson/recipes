package ru.otus.recipes.repository;


import org.springframework.stereotype.Repository;
import ru.otus.recipes.domain.Course;

@Repository
public interface CourseRepository extends CommonRepository<Course> {
}
