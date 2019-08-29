package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.IngredientNutritionalInformationRepository;
import ru.otus.recipes.repository.IngredientRepository;
import ru.otus.recipes.service.mapper.IngredientMapper;


@Service
public class IngredientService extends AbstractService <IngredientDto, Ingredient,  IngredientRepository, IngredientMapper>{

    private final IngredientNutritionalInformationRepository ingredientNutritionalInformationRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository,
                             IngredientNutritionalInformationRepository ingredientNutritionalInformationRepository,
                             IngredientMapper ingredientMapper) {
        super(ingredientRepository, ingredientMapper, Ingredient.class);
        this.ingredientNutritionalInformationRepository = ingredientNutritionalInformationRepository;
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        try {
            ingredientNutritionalInformationRepository.deleteByIngredientId(id);
            super.getRepository().deleteById(id);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            throw new EntityNotFoundException(String.format("No %s entities found!", Ingredient.class.getTypeName()));
        }
    }

    @Override
    public void deleteAll() throws EntityNotFoundException {
        try {
            ingredientNutritionalInformationRepository.deleteAll();
            super.getRepository().deleteAll();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new EntityNotFoundException(String.format("No %s entities found!", Recipe.class.getTypeName()));
        }
    }
}
