package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.service.LevelService;

@RestController
@RequestMapping("/meals")
public class MealController extends AbstractController<Level, LevelService> {
    public MealController(LevelService service) {
        super(service);
    }
}