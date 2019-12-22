package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.repository.MenuRepository;
import ru.otus.recipes.service.MealService;
import ru.otus.recipes.service.MenuService;
import ru.otus.recipes.service.RecipeService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MealRecipeMapper extends AbstractMapper<MealRecipeDto, MealRecipe> {
    private final ModelMapper mapper;
    private final RecipeMapper recipeMapper;
    private final MealService mealService;
    private final RecipeService recipeService;
    private final MenuService menuService;

    MealRecipeMapper(ModelMapper mapper,
                     RecipeMapper recipeMapper, @Lazy MealService mealService,
                     @Lazy RecipeService recipeService, @Lazy MenuService menuService) {
        super(MealRecipe.class, MealRecipeDto.class);
        this.mapper = mapper;
        this.recipeMapper = recipeMapper;
        this.mealService = mealService;
        this.recipeService = recipeService;
        this.menuService = menuService;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(MealRecipe.class, MealRecipeDto.class)
                .addMappings(m -> m.skip(MealRecipeDto::setRecipes))
                .addMappings(m -> m.skip(MealRecipeDto::setMenuId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(MealRecipeDto.class, MealRecipe.class)
                .addMappings(m -> m.skip(MealRecipe::setMeal))
                .addMappings(m -> m.skip(MealRecipe::setRecipes))
                .addMappings(m -> m.skip(MealRecipe::setMenu))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(MealRecipe source, MealRecipeDto destination) {
        List<RecipeDto> recipeDtoList = source.getRecipes().stream().map(recipeMapper::toDto).collect(Collectors.toList());
        destination.setRecipes(new HashSet<>(recipeDtoList));
        Optional<Menu> menu = Optional.ofNullable(source.getMenu());
        destination.setMenuId(menu.map(Menu::getId).orElse(null));

    }

    @Override
    void mapSpecificFields(MealRecipeDto source, MealRecipe destination) {
        destination.setMeal(mealService.getEntityById(source.getMealId()));
        List<Recipe> recipes = Collections.emptyList();
        if (source.getRecipes() !=null){
            recipes = recipeService.getAllEntitiesById(source.getRecipes()
                    .stream().map(RecipeDto::getId).collect(Collectors.toList()));
        }
        destination.setRecipes(recipes);
        destination.setMenu(source.getMenuId() != null ? menuService.getEntityById(source.getMenuId()) : null);
    }
}
