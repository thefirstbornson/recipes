package ru.otus.recipes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.dto.IngredientDto;

import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class IngredientServiceImplTest {
    @Autowired
    private IngredientService ingredientService;

    @Test
    void createIngredient() {
        HashMap<String,String> nutritionalAmountMap= new HashMap<>();
        nutritionalAmountMap.put("1","1");
        nutritionalAmountMap.put("2","1");
        nutritionalAmountMap.put("3","1");
        IngredientDto ingredientDto = new IngredientDto(1,"яйцо",nutritionalAmountMap);
        Ingredient ingredient = ingredientService.createIngredient(ingredientDto);
        System.out.println(ingredient);
    }
}