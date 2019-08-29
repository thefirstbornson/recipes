package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.dto.MealDto;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.service.mapper.MealMapper;

@Service
public class MealService extends AbstractService <MealDto,Meal, MealRepository, MealMapper>{
    @Autowired
    public MealService(MealRepository repository, MealMapper mealMapper) {
        super(repository, mealMapper, Meal.class);
    }
}
