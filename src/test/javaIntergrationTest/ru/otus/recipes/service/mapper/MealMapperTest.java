package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.dto.MealDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class MealMapperTest {

    private final Meal meal = new Meal(0, "mealName");
    private final MealDto mealDto = new MealDto("mealName");

    @Autowired
    private MealMapper mealMapper;

    @Test
    void toEntity() {
        Meal mealFromDto = mealMapper.toEntity(mealDto);
        assertEquals(mealFromDto.getId(), this.meal.getId());
    }

    @Test
    void toDto() {
        MealDto mealDtoFromEntity = mealMapper.toDto(meal);
        assertEquals(mealDtoFromEntity.getMeal(), meal.getMeal());
    }
}