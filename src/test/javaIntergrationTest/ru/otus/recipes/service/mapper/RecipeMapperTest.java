package ru.otus.recipes.service.mapper;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.*;
import ru.otus.recipes.service.RecipeService;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class RecipeMapperTest {
    private RecipeDto recipeDto;
    private Recipe recipe;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeService recipeService;
    @Autowired
    private RecipeMapper recipeMapper;

    @BeforeEach
    void setUp() throws EntityNotFoundException {
        recipe = recipeRepository.findById(1L).get();
        recipeDto = recipeService.findById(1L);
    }

    @Test
    void toDto() {
        RecipeDto recipeDtoFromEntity = recipeMapper.toDto(recipe);
        assertEquals(recipeDtoFromEntity.getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"),
                recipe.getRecipeIngredients().stream().findFirst().get().getAmount());
    }

    @Test
    void toEntity() {
        Recipe recipeFromDto = recipeMapper.toEntity(recipeDto);
        assertEquals(recipeFromDto.getRecipeIngredients().stream().findFirst().get().getAmount(),
                recipeDto.getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"));
    }
}