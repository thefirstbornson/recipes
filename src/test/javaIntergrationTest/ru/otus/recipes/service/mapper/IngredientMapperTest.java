package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class IngredientMapperTest {

    private final NutritionalInformation nutritionalInformation = new NutritionalInformation( "nutritionName");
    private final IngredientNutritionalInformation ingredientNutritionalInformation =
            new IngredientNutritionalInformation(nutritionalInformation,10);
    private final Ingredient ingredient =
            new Ingredient("ingredientName", Collections.singletonList(ingredientNutritionalInformation));
    private final IngredientDto ingredientDto =
            new IngredientDto("ingredientName", new HashMap<>(0,10));

    @Autowired
    private IngredientMapper ingredientMapper;

    @Test
    void toEntity() {
        Ingredient IngredientFromDto = ingredientMapper.toEntity(ingredientDto);
        assertEquals(IngredientFromDto.getName(), this.ingredient.getName());
    }

    @Test
    void toDto() {
        IngredientDto IngredientDtoFromEntity = ingredientMapper.toDto(ingredient);
        assertEquals(IngredientDtoFromEntity.getNutritionalIdsndAmountMap().get(0L),
               ingredient.getIngredientNutritionalInformations().stream()
                       .map(IngredientNutritionalInformation::getAmount).findFirst().get());
    }
}