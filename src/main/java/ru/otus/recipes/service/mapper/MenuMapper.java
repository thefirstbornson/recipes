package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.MenuDto;
import ru.otus.recipes.service.MealRecipeService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MenuMapper extends AbstractMapper<MenuDto, Menu> {
    private final ModelMapper mapper;
    private final MealRecipeService mealRecipeService;
    private final MealRecipeMapper mealRecipeMapper;

    MenuMapper(ModelMapper mapper, @Lazy MealRecipeService mealRecipeService, MealRecipeMapper mealRecipeMapper) {
        super(Menu.class, MenuDto.class);
        this.mapper = mapper;
        this.mealRecipeService = mealRecipeService;
        this.mealRecipeMapper = mealRecipeMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Menu.class, MenuDto.class)
                .addMappings(m -> m.skip(MenuDto::setMealRecipes))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(MenuDto.class, Menu.class)
                .addMappings(m -> m.skip(Menu::setMealRecipes))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Menu source, MenuDto destination) {
        List<MealRecipeDto> mealRecipeDtoList = Collections.emptyList();
        if (source.getMealRecipes()!=null) {
            mealRecipeDtoList = source.getMealRecipes().stream().map(mealRecipeMapper::toDto).collect(Collectors.toList());
        }
        destination.setMealRecipes(mealRecipeDtoList);
    }

    @Override
    void mapSpecificFields(MenuDto source, Menu destination) {
        List<MealRecipe> mealRecipeList = Collections.emptyList();
        if (source.getMealRecipes()!=null) {
            mealRecipeList = mealRecipeService.getAllEntitiesByIds(source.getMealRecipes()
                    .stream()
                    .map(MealRecipeDto::getId)
                    .collect(Collectors.toList()));
        }
        destination.setMealRecipes(mealRecipeList);
    }
}
