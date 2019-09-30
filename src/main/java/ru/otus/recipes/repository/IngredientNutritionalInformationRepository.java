package ru.otus.recipes.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.IngredientNutritionalInformation;

@Transactional
public interface IngredientNutritionalInformationRepository extends  CommonRepository<IngredientNutritionalInformation> {
    void deleteByIngredientId(long id);
}
