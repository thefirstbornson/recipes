package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.repository.MenuRepository;
import ru.otus.recipes.repository.NutritionalInformationRepository;
import ru.otus.recipes.repository.RecipeRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealRecipeMapper extends AbstractMapper<MealRecipeDto, MealRecipe> {
    private final ModelMapper mapper;
    private final MealMapper mealMapper;
    private final RecipeMapper recipeMapper;
    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;
    private final MenuRepository menuRepository;


    MealRecipeMapper(ModelMapper mapper, NutritionalInformationRepository nutritionalInformationRepository,
                     MealMapper mealMapper, RecipeMapper recipeMapper, MealRepository mealRepository, RecipeRepository recipeRepository, MenuRepository menuRepository) {
        super(MealRecipe.class, MealRecipeDto.class);
        this.mapper = mapper;
        this.mealMapper = mealMapper;
        this.recipeMapper = recipeMapper;
        this.mealRepository = mealRepository;
        this.recipeRepository = recipeRepository;
        this.menuRepository = menuRepository;
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
        destination.setMeal(mealRepository.getOne(source.getMealId()));
        List<Recipe> recipes = recipeRepository.findByIdIn(source.getRecipes()
                .stream().map(RecipeDto::getId).collect(Collectors.toList()));
        destination.setRecipes(new HashSet<>(recipes));
        destination.setMenu(Optional.ofNullable(source.getMenuId()).isPresent() ? menuRepository.getOne(source.getMenuId()): null);
    }
}
