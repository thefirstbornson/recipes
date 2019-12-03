package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.DailyMenu;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.DailyMenuDto;
import ru.otus.recipes.dto.MenuDto;
import ru.otus.recipes.service.DailyMenuService;
import ru.otus.recipes.service.MenuService;


@RestController
@RequestMapping("/daily-menus")
public class DailyMenuController extends AbstractController<DailyMenu, DailyMenuService, DailyMenuDto>{
    public DailyMenuController(DailyMenuService dailyMenuService) {
        super(dailyMenuService, DailyMenu.class);
    }
}
