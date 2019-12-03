package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.MenuDto;
import ru.otus.recipes.service.MealRecipeService;
import ru.otus.recipes.service.MenuService;


@RestController
@RequestMapping("/menus")
public class MenuController extends AbstractController<Menu, MenuService, MenuDto>{
    public MenuController(MenuService menuService) {
        super(menuService, Menu.class);
    }
}
