package ru.otus.recipes.service.dtoconversion;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.dto.CuisineDto;
import ru.otus.recipes.dto.FoodCategoryDto;

@Service
public class FoodCategoryMapper extends AbstractMapper<FoodCategoryDto, FoodCategory> {
    FoodCategoryMapper() {
        super(FoodCategory.class, FoodCategoryDto.class);
    }
}
