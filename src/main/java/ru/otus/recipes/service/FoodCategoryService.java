package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.repository.CuisineRepository;
import ru.otus.recipes.repository.FoodCategoryRepository;

@Service
public class FoodCategoryService extends AbstractService <FoodCategory, FoodCategoryRepository>{
    public FoodCategoryService(FoodCategoryRepository repository) {
        super(repository);
    }
}
