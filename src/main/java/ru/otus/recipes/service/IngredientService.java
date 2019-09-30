package ru.otus.recipes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(Ingredient.class);

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
            log.info(String.format("Start removing %s entity from join table", Ingredient.class));
            ingredientNutritionalInformationRepository.deleteByIngredientId(id);
            log.info("Removal from join table successful");
            super.getRepository().deleteById(id);
        } catch (EmptyResultDataAccessException e){
            log.error("Empty result returned",e);
            throw new EntityNotFoundException(String.format("No %s entities found!", Ingredient.class.getTypeName()));
        }
    }

    @Override
    public void deleteAll() throws EntityNotFoundException {
        try {
            log.info(String.format("Start removing %s entities from join table", Ingredient.class));
            ingredientNutritionalInformationRepository.deleteAll();
            log.info("Removal from join table successful");
            super.getRepository().deleteAll();
        } catch (IllegalArgumentException e){
            log.error(String.format("Error returned while removing %s entity ", Ingredient.class),e);
            throw new EntityNotFoundException(String.format("No %s entities found!", Recipe.class.getTypeName()));
        }
    }
}
