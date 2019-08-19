package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.repository.CourseRepository;
import ru.otus.recipes.repository.CuisineRepository;

@Service
public class CuisineService extends AbstractService <Cuisine, CuisineRepository>{
    public CuisineService(CuisineRepository repository) {
        super(repository);
    }
}
