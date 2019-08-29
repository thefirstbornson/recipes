package ru.otus.recipes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.*;
import ru.otus.recipes.service.mapper.RecipeMapper;

@Service
public class RecipeService  extends AbstractService <RecipeDto,Recipe,  RecipeRepository, RecipeMapper> {

    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository,
                          RecipeMapper recipeMapper) {
        super(recipeRepository, recipeMapper, Recipe.class);
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        try {
            recipeIngredientRepository.deleteByRecipeId(id);
            super.getRepository().deleteById(id);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            throw new EntityNotFoundException(String.format("No %s entities found!", Recipe.class.getTypeName()));
        }
    }

    @Override
    public void deleteAll() throws EntityNotFoundException {
        try {
            recipeIngredientRepository.deleteAll();
            super.getRepository().deleteAll();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new EntityNotFoundException(String.format("No %s entities found!", Recipe.class.getTypeName()));
        }
    }
}
