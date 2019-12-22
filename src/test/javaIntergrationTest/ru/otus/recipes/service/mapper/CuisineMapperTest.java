package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.dto.CuisineDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class CuisineMapperTest {

    private final Cuisine cuisineName = new Cuisine(0L, "cuisineName");
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