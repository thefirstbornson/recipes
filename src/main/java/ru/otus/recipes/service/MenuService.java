package ru.otus.recipes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.MenuDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRecipeRepository;
import ru.otus.recipes.repository.MenuRepository;
import ru.otus.recipes.service.mapper.MenuMapper;


@Service
public class MenuService extends AbstractService <MenuDto, Menu, MenuRepository, MenuMapper> {
    private final Logger log = LoggerFactory.getLogger(Menu.class);
    private final MealRecipeRepository mealRecipeRepository;

    public MenuService(MenuRepository repository, MenuMapper mapper, MealRecipeRepository mealRecipeRepository) {
        super(repository, mapper, Menu.class);
        this.mealRecipeRepository = mealRecipeRepository;
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        log.info(String.format("Start removing %s entity from mealrecipes", Menu.class));
        mealRecipeRepository.deleteMenuFromMealRecipes(id);
        super.deleteById(id);
        log.info("Removal from mealrecipes successful");
    }

    @Override
    public void deleteAll() {
        log.info(String.format("Start removing %s entity from mealrecipes", Menu.class));
        mealRecipeRepository.deleteAllMenusFromMealRecipes();
        log.info("Removal from join table successful");
        super.getRepository().deleteAll();
    }
}
