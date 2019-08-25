package ru.otus.recipes.service.dtoconversion;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.*;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.dto.RecipeDto;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecipeMapper extends AbstractMapper<RecipeDto,Recipe> {

    private final ModelMapper mapper;

    RecipeMapper(ModelMapper mapper) {
        super(Recipe.class, RecipeDto.class);
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Recipe.class, RecipeDto.class)
                .addMappings(m -> m.skip(RecipeDto::setDroidId)).setPostConverter(toDtoConverter());
//        mapper.createTypeMap(RecipeDto.class, Recipe.class)
//                .addMappings(m -> m.skip(Recipe::setDroid)).setPostConverter(toEntityConverter());
    }

    @Override
    public RecipeDto convertToDto(Recipe recipe) {
         long id = recipe.getId();
         String name = recipe.getName();
         String description = recipe.getDescription();
         String instructions = recipe.getInstructions();
         int cooktime = recipe.getCooktime();
         long levelId = recipe.getLevel().getId();
         long cuisineId = recipe.getCuisine().getId();
         int rating = recipe.getRating();
         String imagepath=recipe.getImagepath();
         List<Long> courseIdList = recipe.getCourses().stream().map(Course::getId).collect(Collectors.toList());
         List<Long> foodCategoryIdList = recipe.getFoodCategories().stream().map(FoodCategory::getId).collect(Collectors.toList());
         List<Long> mealIdList= recipe.getMeals().stream().map(Meal::getId).collect(Collectors.toList());
         Map<String,Map<String,Long>> ingredientIdAndAmountMeasurementMap = new HashMap<>();
         recipe.getRecipeIngredients()
                .forEach(recipeIngr -> ingredientIdAndAmountMeasurementMap.
                        put( String.valueOf(recipeIngr.getIngredient().getId()),
                                new HashMap<>(){{
                                    put( "measurement_id", recipeIngr.getMeasurement().getId() );
                                    put("amount", (long) recipeIngr.getAmount());
                                }}));
        return new RecipeDto(id, name, description, instructions, cooktime, levelId, cuisineId, rating, imagepath,
                ingredientIdAndAmountMeasurementMap, courseIdList, foodCategoryIdList, mealIdList) ;
    }


    @Override
    void mapSpecificFields(Recipe source, RecipeDto destination) {
        super.mapSpecificFields(source, destination);
    }

    @Override
    void mapSpecificFields(RecipeDto source, Recipe destination) {
        super.mapSpecificFields(source, destination);
    }
}
