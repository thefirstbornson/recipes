package ru.otus.recipes.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.service.MenuService;
import ru.otus.recipes.service.RecipeService;
import ru.otus.recipes.service.mapper.MealRecipeMapper;
import ru.otus.recipes.service.mapper.MenuMapper;
import ru.otus.recipes.service.strategy.RandomStrategy;
import ru.otus.recipes.service.strategy.Strategy;

import javax.persistence.EntityManager;
import java.util.*;

import static org.assertj.core.util.DateUtil.now;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
class CommonTest {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    MealRecipeRepository mealRecipeRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    DailyMenuRepository dailyMenuRepository;
    @Autowired
    RecipeService recipeService;
    @Autowired
    MenuService menuService;
    @Autowired
    EntityManager entityManager;
    @Autowired
    MealRecipeMapper mealRecipeMapper;
    @Autowired
    MenuMapper menuMapper;

    @BeforeEach
    void setUp(){


    }

    @Test
    @Transactional
    void saveMealRecipeTest() throws EntityExistsException, EntityNotFoundException {
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

            RecipeDto recipe = recipeService.save(recipeDto);


        MealRecipe mealRecipe = new MealRecipe();
        mealRecipe.setMeal(mealRepository.findById(1L).get());
        List<Recipe> recipes = new ArrayList<>(Collections.singletonList(recipeRepository.findById(1L).get()));
        mealRecipe.setRecipes(recipes);
        mealRecipe.setMenu(null);
        mealRecipeRepository.save(mealRecipe);
        MealRecipe mealRecipe1 = mealRecipeRepository.findById(1L).get();

        Menu menu = new Menu();
        List<MealRecipe> mealRecipes = Collections.singletonList(mealRecipe1);
        menu.setMealRecipes(mealRecipes);
        menu = menuRepository.save(menu);
//        menu.getMealRecipes().clear();

        menuService.deleteById(menu.getId());
//        menuRepository.delete(menu);

        DailyMenu dailyMenu = new DailyMenu();
        dailyMenu.setMenu(menu);
        dailyMenu.setDate(now());
        dailyMenuRepository.save(dailyMenu);


//        MealRecipeDto mealRecipeDtoFromEntity = mealRecipeMapper.toDto(mealRecipe1);
//        MealRecipe mealRecipe2 = mealRecipeMapper.toEntity(mealRecipeDtoFromEntity);
//        mealRecipeRepository.save(mealRecipe2);

//        Assert.assertFalse(mealRecipe1.getRecipes().isEmpty());
    }

    @Test
    @Transactional
    void strategyTest(){
        List<Meal> mealList = mealRepository.findAll();
        Strategy strategy = new RandomStrategy(mealList, recipeRepository);
        Menu menu = strategy.createMenu();
        menuRepository.save(menu);
        System.out.println(menuMapper.toDto(menu));
    }
}