package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.MenuDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MenuRepository;
import ru.otus.recipes.service.MenuService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class MenuMapperTest {
    private MenuDto menuDto;
    private Menu menu;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuService menuService;
    @Autowired
    private MenuMapper menuMapper;

    @BeforeEach
    void setUp() throws EntityNotFoundException {
        menuDto = menuService.findById(1L);
        menu = menuRepository.findById(1L).get();
    }

    @Test
    void toDto() {
        MenuDto menuDtoFromEntity = menuMapper.toDto(menu);
        List<MealRecipeDto> meaRecipesFromDto = new ArrayList<>(menuDtoFromEntity.getMealRecipes());
        assertEquals(meaRecipesFromDto.get(0).getRecipes()
                                        .stream()
                                        .findFirst()
                                        .get()
                                        .getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"),
                    new ArrayList<>(menu.getMealRecipes()).get(0).getRecipes()
                                        .stream()
                                        .findFirst()
                                        .get()
                                        .getRecipeIngredients().stream().findFirst().get().getAmount());
    }

    @Test
    void toEntity() {
        Menu menuFromDto = menuMapper.toEntity(menuDto);
        assertEquals(new ArrayList<>(menuFromDto.getMealRecipes()).get(0).getRecipes()
                                        .stream()
                                        .findFirst()
                                        .get().getRecipeIngredients().stream().findFirst().get().getAmount(),
                    menuDto.getMealRecipes().stream().findFirst().get().getRecipes()
                                        .stream()
                                        .findFirst()
                                        .get().getIngredientIdAndMeasurementIdAmountMap().get(String.valueOf(1)).get("amount"));
    }
}