package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.dto.FoodCategoryDto;
import ru.otus.recipes.dto.IngredientNutritionalDto;
import ru.otus.recipes.repository.FoodCategoryRepository;
import ru.otus.recipes.repository.IngredientNutritionalInformationRepository;
import ru.otus.recipes.service.mapper.FoodCategoryMapper;
import ru.otus.recipes.service.mapper.IngredientNutritionalMapper;

@Service
public class IngredientNutritionalInformationService extends AbstractService <IngredientNutritionalDto, IngredientNutritionalInformation,
        IngredientNutritionalInformationRepository, IngredientNutritionalMapper>{
    @Autowired
    public IngredientNutritionalInformationService(IngredientNutritionalInformationRepository repository , IngredientNutritionalMapper mapper) {
        super(repository, mapper, IngredientNutritionalInformation.class);
    }
}
