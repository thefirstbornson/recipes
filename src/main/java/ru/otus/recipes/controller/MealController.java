package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.dto.MealDto;
import ru.otus.recipes.service.MealService;

@RestController
@RequestMapping("/meals")
public class MealController extends AbstractController<Meal, MealService, MealDto> {
    public MealController(MealService service) {
        super(service, Meal.class);
    }
}