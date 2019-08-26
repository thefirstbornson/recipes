package ru.otus.recipes.service;

import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.dto.IngredientDto;

import java.util.List;

@Service
public class IngredientService extends AbstractService <IngredientDto, Ingredient,  IngredientRepository, IngredientMapper>{
    @Autowired
    private final IngredientNutritionalInformationRepository ingredientNutritionalInformationRepository;

    public IngredientService(IngredientRepository ingredientRepository,
                             IngredientNutritionalInformationRepository ingredientNutritionalInformationRepository,
                             IngredientMapper ingredientMapper) {
        super(ingredientRepository, ingredientMapper);
        this.ingredientNutritionalInformationRepository = ingredientNutritionalInformationRepository;
    }

    @Override
    public void deleteById(Long id) {
        ingredientNutritionalInformationRepository.deleteByIngredientId(id);
        super.getRepository().deleteById(id);
    }

    @Override
    public void deleteAll() {
        ingredientNutritionalInformationRepository.deleteAll();
        super.getRepository().deleteAll();
    }
}
