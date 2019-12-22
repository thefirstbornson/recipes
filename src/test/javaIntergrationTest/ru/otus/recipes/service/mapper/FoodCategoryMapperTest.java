package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.dto.CuisineDto;
import ru.otus.recipes.dto.FoodCategoryDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class FoodCategoryMapperTest {

    private final FoodCategory foodCategory = new FoodCategory(0L, "foodCategoryName");
    private final FoodCategoryDto foodCategoryDto = new FoodCategoryDto("foodCategoryName");

    @Autowired
    private FoodCategoryMapper foodCategoryMapper;

    @Test
    void toEntity() {
        FoodCategory foodCategoryFromDto = foodCategoryMapper.toEntity(foodCategoryDto);
        assertEquals(foodCategoryFromDto.getId(), this.foodCategory.getId());
    }

    @Test
    void toDto() {
        FoodCategoryDto foodCategoryDtoFromEntity = foodCategoryMapper.toDto(foodCategory);
        assertEquals(foodCategoryDtoFromEntity.getFoodCategory(), foodCategory.getFoodCategory());
    }
}