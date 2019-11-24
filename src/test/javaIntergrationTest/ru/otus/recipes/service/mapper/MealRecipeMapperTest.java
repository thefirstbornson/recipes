package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.MealDto;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRecipeRepository;
import ru.otus.recipes.repository.RecipeRepository;
import ru.otus.recipes.service.MealRecipeService;
import ru.otus.recipes.service.RecipeService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class MealRecipeMapperTest {
    private MealRecipeDto mealRecipeDto;
    private MealRecipe mealRecipe;
    @Autowired
    MealRecipeRepository mealRecipeRepository;
    @Autowired
    MealRecipeService mealRecipeService;
    @Autowired
    private MealRecipeMapper mealRecipeMapper;

    @BeforeEach
    void setUp() throws EntityNotFoundException {
        mealRecipeDto = mealRecipeService.findById(1L);
        mealRecipe = mealRecipeRepository.findById(1L).get();
    }

    @Test
    void toDto() {
        MealRecipeDto mealRecipeDtoFromEntity = mealRecipeMapper.toDto(mealRecipe);
        List<RecipeDto> recipesFromDto = new ArrayList<>(mealRecipeDtoFromEntity.getRecipes());
        assertEquals( recipesFromDto.get(0).getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"),
                new ArrayList<>(mealRecipe.getRecipes()).get(0).getRecipeIngredients().stream().findFirst().get().getAmount());
    }

    @Test
    void toEntity() {
        MealRecipe mealRecipeFromDto = mealRecipeMapper.toEntity(mealRecipeDto);
        assertEquals(new ArrayList<>(mealRecipeFromDto.getRecipes()).get(0).getRecipeIngredients().stream().findFirst().get().getAmount(),
                mealRecipeDto.getRecipes().stream().findFirst().get().getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"));
    }
}