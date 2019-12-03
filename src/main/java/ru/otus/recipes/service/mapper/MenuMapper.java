package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.MenuDto;
import ru.otus.recipes.repository.MealRecipeRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MenuMapper extends AbstractMapper<MenuDto, Menu> {
    private final ModelMapper mapper;
    private final MealRecipeRepository mealRecipeRepository;
    private final MealRecipeMapper mealRecipeMapper;

    MenuMapper(ModelMapper mapper, MealRecipeRepository mealRecipeRepository, MealRecipeMapper mealRecipeMapper) {
        super(Menu.class, MenuDto.class);
        this.mapper = mapper;
        this.mealRecipeRepository = mealRecipeRepository;
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
        if (Optional.ofNullable(source.getMealRecipes()).isPresent()) {
        List<MealRecipeDto> mealRecipeDtoList = source.getMealRecipes().stream().map(mealRecipeMapper::toDto).collect(Collectors.toList());
        destination.setMealRecipes(new HashSet<>(mealRecipeDtoList));
        } else {
            destination.setMealRecipes(null);
        }
    }

    @Override
    void mapSpecificFields(MenuDto source, Menu destination) {
        if (Optional.ofNullable(source.getMealRecipes()).isPresent()) {
            List<MealRecipe> mealRecipeList = mealRecipeRepository.findByIdIn(source.getMealRecipes()
                    .stream()
                    .map(MealRecipeDto::getId)
                    .collect(Collectors.toList()));
            destination.setMealRecipes(new HashSet<>(mealRecipeList));
        } else {
            destination.setMealRecipes(null);
        }
    }
}
