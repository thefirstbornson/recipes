package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.RecipeTestConfiguration;
import ru.otus.recipes.domain.Course;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.dto.CourseDto;
import ru.otus.recipes.dto.CuisineDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(properties= "spring.main.allow-bean-definition-overriding=true")
//@ContextConfiguration (classes = {RecipeTestConfiguration.class})
class CuisineMapperTest {

    private final Cuisine cuisineName = new Cuisine(0, "cuisineName");
    private final CuisineDto cuisineDto = new CuisineDto("cuisineName");

    @Autowired
    private CuisineMapper cuisineMapper;

    @Test
    void toEntity() {
        Cuisine cuisine = cuisineMapper.toEntity(cuisineDto);
        assertEquals(cuisine.getId(), cuisineName.getId());
    }

    @Test
    void toDto() {
        CuisineDto cuisineDtoDto = cuisineMapper.toDto(cuisineName);
        assertEquals(cuisineDtoDto.getCuisine(), cuisineName.getCuisine());
    }
}