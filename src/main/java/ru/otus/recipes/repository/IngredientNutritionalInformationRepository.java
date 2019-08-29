package ru.otus.recipes.repository;

import ru.otus.recipes.domain.IngredientNutritionalInformation;

public interface IngredientNutritionalInformationRepository extends  CommonRepository<IngredientNutritionalInformation> {
    void deleteByIngredientId(long id);
}
