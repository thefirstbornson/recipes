package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.repository.LevelRepository;
import ru.otus.recipes.repository.MealRepository;

@Service
public class MealService extends AbstractService <Meal, MealRepository>{
    public MealService(MealRepository repository) {
        super(repository);
    }
}
