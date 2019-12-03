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
            log.info(String.format("Start removing %s entity from join table", Ingredient.class));
            Ingredient ingredient = super.getRepository().findById(id).orElseThrow(()->new EntityNotFoundException(
                    String.format("No %s entity with id %d found!", Ingredient.class.getTypeName(),id)));
            ingredientNutritionalInformationRepository.deleteByIngredientId(id);
            log.info("Removal from join table successful");
            super.getRepository().delete(ingredient);
    }

    @Override
    public void deleteAll() {
            log.info(String.format("Start removing %s entities from join table", Ingredient.class));
            ingredientNutritionalInformationRepository.deleteAll();
            log.info("Removal from join table successful");
            super.getRepository().deleteAll();
    }
}
