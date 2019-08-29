package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.dto.FoodCategoryDto;
import ru.otus.recipes.repository.FoodCategoryRepository;
import ru.otus.recipes.service.mapper.FoodCategoryMapper;

@Service
public class FoodCategoryService extends AbstractService <FoodCategoryDto, FoodCategory, FoodCategoryRepository, FoodCategoryMapper>{
    @Autowired
    public FoodCategoryService(FoodCategoryRepository repository , FoodCategoryMapper foodCategoryMapper) {
        super(repository, foodCategoryMapper, FoodCategory.class);
    }
}
