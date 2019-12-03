package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.DailyMenu;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.dto.DailyMenuDto;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.DailyMenuRepository;
import ru.otus.recipes.service.DailyMenuService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class DailyMenuMapperTest {
    private DailyMenuDto dailyMenuDto;
    private DailyMenu dailyMenu;
    @Autowired
    DailyMenuRepository dailyMenuRepository;
    @Autowired
    DailyMenuService dailyMenuService;
    @Autowired
    private DailyMenuMapper dailyMenuMapper;

    @BeforeEach
    void setUp() throws EntityNotFoundException {
        dailyMenuDto = dailyMenuService.findById(1L);
        dailyMenu = dailyMenuRepository.findById(1L).get();
    }

    @Test
    void toDto() {
        DailyMenuDto dailyMenuDtoDtoFromEntity = dailyMenuMapper.toDto(dailyMenu);
        assertEquals(dailyMenu.getMenu().getId(),dailyMenuDtoDtoFromEntity.getMenuId());
    }

    @Test
    void toEntity() {
        DailyMenu dailyMenuFromDto = dailyMenuMapper.toEntity(dailyMenuDto);
        assertEquals(dailyMenuDto.getMenuId(),dailyMenuFromDto.getMenu().getId());
    }
}