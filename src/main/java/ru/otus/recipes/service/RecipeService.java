package ru.otus.recipes.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.RecipeIngredientRepository;
import ru.otus.recipes.repository.RecipeRepository;
import ru.otus.recipes.service.mapper.RecipeMapper;

@Service
public class RecipeService  extends AbstractService <RecipeDto, Recipe, RecipeRepository, RecipeMapper> {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final Logger log = LoggerFactory.getLogger(Recipe.class);

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository,
                          RecipeMapper recipeMapper) {
        super(recipeRepository, recipeMapper, Recipe.class);
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        try {
            log.info(String.format("Start removing %s entity from join table", Recipe.class));
            recipeIngredientRepository.deleteByRecipeId(id);
            log.info("Removal from join table successful");
            super.getRepository().deleteById(id);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            throw new EntityNotFoundException(String.format("No %s entities found!", Recipe.class.getTypeName()));
        }
    }

    @Override
    public void deleteAll() throws EntityNotFoundException {
        try {
            log.info(String.format("Start removing %s entities from join table", Recipe.class));
            recipeIngredientRepository.deleteAll();
            log.info("Removal from join table successful");
            super.getRepository().deleteAll();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new EntityNotFoundException(String.format("No %s entities found!", Recipe.class.getTypeName()));
        }
    }
}
