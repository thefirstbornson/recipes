package ru.otus.recipes.service.mapper;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.dto.FoodCategoryDto;

@Service
public class FoodCategoryMapper extends AbstractMapper<FoodCategoryDto, FoodCategory> {
    FoodCategoryMapper() {
        super(FoodCategory.class, FoodCategoryDto.class);
    }
}
