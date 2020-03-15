package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.service.MealService;
import ru.otus.recipes.service.RecipeService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealRecipeMapper extends AbstractMapper<MealRecipeDto, MealRecipe> {
    private final ModelMapper mapper;
    private final RecipeMapper recipeMapper;
    private final RecipeService recipeService;
    private final MealService mealService;

    private final MealMapper mealMapper;

    MealRecipeMapper(ModelMapper mapper,
                     RecipeMapper recipeMapper, RecipeService recipeService, MealService mealService, MealMapper mealMapper) {
        super(MealRecipe.class, MealRecipeDto.class);
        this.mapper = mapper;
        this.recipeMapper = recipeMapper;
        this.mealMapper = mealMapper;
        this.recipeService = recipeService;
        this.mealService = mealService;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(MealRecipe.class, MealRecipeDto.class)
                .addMappings(m -> m.skip(MealRecipeDto::setMeal))
                .addMappings(m -> m.skip(MealRecipeDto::setRecipes))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(MealRecipeDto.class, MealRecipe.class)
                .addMappings(m -> m.skip(MealRecipe::setMeal))
                .addMappings(m -> m.skip(MealRecipe::setRecipes))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(MealRecipe source, MealRecipeDto destination) {
        destination.setMeal(mealMapper.toDto(source.getMeal()));
        List<RecipeDto> recipeDtoList = source.getRecipes().stream().map(recipeMapper::toDto).collect(Collectors.toList());
        destination.setRecipes(new HashSet<>(recipeDtoList));
    }

    @Override
    void mapSpecificFields(MealRecipeDto source, MealRecipe destination) {
        destination.setMeal(mealService.getEntityById(source.getMeal().getId()));
        List<Recipe> recipes = Collections.emptyList();
        if (source.getRecipes() !=null){
            recipes = recipeService.getAllEntitiesByIds(source.getRecipes()
                    .stream()
                    .map(RecipeDto::getId)
                    .collect(Collectors.toList()));
        }
        destination.setRecipes(recipes);
    }
}
