package ru.otus.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.service.RecipeService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class RecipesApplication {

    public static void main(String[] args) {
        //SpringApplication.run(RecipesApplication.class, args);
        ApplicationContext context = SpringApplication.run(RecipesApplication.class);
        RecipeService recipeService = context.getBean(RecipeService.class);
        Map<String,Map<String,String>> ingredients = new HashMap<>();
        ingredients.put("1", Stream.of(new String[][] {
                { "measurement_id", "1" },
                { "amount", "200" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1])));
        ingredients.put("2", Stream.of(new String[][] {
                { "measurement_id", "1" },
                { "amount", "300" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

        RecipeDto recipeDto = new RecipeDto(0,"1","2","3",30,1,1,1,"",
                ingredients, Arrays.asList(1L,2L),Arrays.asList(1L,2L),Arrays.asList(1L,2L));

        Recipe recipe = recipeService.createRecipe(recipeDto);


    }

}
