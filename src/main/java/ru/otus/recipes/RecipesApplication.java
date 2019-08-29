package ru.otus.recipes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityMapperException;
import ru.otus.recipes.service.RecipeService;

import java.util.Arrays;
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
        Map<String,Map<String,Long>> ingredients = new HashMap<>();
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
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
