package ru.otus.recipes.repository;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityMapperException;
import ru.otus.recipes.service.RecipeService;
import ru.otus.recipes.service.mapper.RecipeMapper;

import javax.persistence.EntityManager;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@ComponentScan({"ru.otus.recipes.repository"})
@TestPropertySource("classpath:application-test.properties")
class MealRecipeTest {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    MealRecipeRepository mealRecipeRepository;
    @Autowired
    RecipeService recipeService;
    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp(){


    }

    @Test
    public void saveMealRecipeTest(){
        Map<String, Map<String,Long>> ingredients = new HashMap<>();
        ingredients.put(String.valueOf(1),  new HashMap<>(){{
            put( "measurement_id", 1L );
            put("amount", 200L);
        }});
        ingredients.put(String.valueOf(2),  new HashMap<>(){{
            put( "measurement_id", 1L );
            put("amount", 200L);
        }});

        RecipeDto recipeDto = new RecipeDto(0,"1","2","3",30,1,1,1,"",
                ingredients, Arrays.asList(1L,2L),Arrays.asList(1L,2L),Arrays.asList(1L,2L));
        try {
            RecipeDto recipe = recipeService.save(recipeDto);
        } catch (EntityMapperException e) {
            e.printStackTrace();
        }

        MealRecipe mealRecipe = new MealRecipe();
        mealRecipe.setMeal(mealRepository.findById(1L).get());
        HashSet<Recipe> recipes = new HashSet<>(Collections.singletonList(recipeRepository.findById(1L).get()));
        mealRecipe.setRecipes(recipes);
        mealRecipeRepository.save(mealRecipe);
        MealRecipe mealRecipe1 = mealRecipeRepository.findById(1L).get();
        Assert.assertTrue(mealRecipe1.getRecipes().isEmpty());

    }


}