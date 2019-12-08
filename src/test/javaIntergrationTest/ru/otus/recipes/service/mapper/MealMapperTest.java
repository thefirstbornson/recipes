package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.dto.MealDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class MealMapperTest {

    private final Meal meal = new Meal(0L, "mealName");
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