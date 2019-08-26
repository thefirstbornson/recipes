package ru.otus.recipes.service.dtoconversion;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.dto.LevelDto;
import ru.otus.recipes.dto.MealDto;

@Service
public class MealMapper extends AbstractMapper<MealDto, Meal> {
    MealMapper() {
        super(Meal.class, MealDto.class);
    }
}
