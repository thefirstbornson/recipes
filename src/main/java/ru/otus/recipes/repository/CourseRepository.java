package ru.otus.recipes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.recipes.domain.Course;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
