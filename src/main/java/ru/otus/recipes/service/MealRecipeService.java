package ru.otus.recipes.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRecipeRepository;
import ru.otus.recipes.service.mapper.MealRecipeMapper;

@Service
public class MealRecipeService extends AbstractService <MealRecipeDto, MealRecipe, MealRecipeRepository, MealRecipeMapper> {

    private final Logger log = LoggerFactory.getLogger(MealRecipeService.class);

    @Autowired
    public MealRecipeService(MealRecipeRepository mealRecipeRepository, MealRecipeMapper mealRecipeMapper) {
        super(mealRecipeRepository, mealRecipeMapper, MealRecipe.class);
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
            log.info(String.format("Start removing %s entity from join table", MealRecipe.class));
            super.getRepository().deleteAllMealRecipes(id);
            log.info("Removal from join table successful");
            super.deleteById(id);
//            super.getRepository().deleteById(id);
    }

    @Override
    public void deleteAll() {
            log.info(String.format("Start removing %s entities from join table", MealRecipe.class));
            super.getRepository().deleteAllMealRecipes();
            log.info("Removal from join table successful");
            super.getRepository().deleteAll();
    }

    Integer deleteMenuFromMealRecipes (Long menuId){
        log.info(String.format("Start removing menu from %s entities", MealRecipe.class));
        return super.getRepository().deleteMenuFromMealRecipes(menuId);
    }

    Integer deleteAllMenusFromMealRecipes (){
        log.info(String.format("Start removing menus  from %s entities", MealRecipe.class));
        return super.getRepository().deleteAllMenusFromMealRecipes();
    }
}
