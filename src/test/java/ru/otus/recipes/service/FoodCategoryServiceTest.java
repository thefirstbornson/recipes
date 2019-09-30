package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.dto.FoodCategoryDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.FoodCategoryRepository;
import ru.otus.recipes.service.mapper.FoodCategoryMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class FoodCategoryServiceTest {

    private FoodCategory persistedFoodCategory;
    private FoodCategoryDto FoodCategoryDto;
    private FoodCategoryDto persistedFoodCategoryDto;

    @MockBean
    private FoodCategoryRepository FoodCategoryRepository;
    @MockBean
    private FoodCategoryMapper FoodCategoryMapper;
    @Bean
    FoodCategoryService foodCategoryService() {
        return new FoodCategoryService(FoodCategoryRepository, FoodCategoryMapper);
    }

    @BeforeEach
    void setUp() {
        FoodCategory FoodCategory = new FoodCategory(0, "FoodCategoryName");
        FoodCategoryDto = new FoodCategoryDto("FoodCategoryName");
        persistedFoodCategory = new FoodCategory(1,"FoodCategoryName");
        persistedFoodCategoryDto = new FoodCategoryDto("FoodCategoryName");
        Mockito.when(FoodCategoryMapper.toEntity(any(FoodCategoryDto.class))).thenReturn(FoodCategory);
    }

    @Test
    @DisplayName("Saving the FoodCategory entity")
    void save() {
        persistedFoodCategoryDto.setId(1L);
        Mockito.when(FoodCategoryRepository.save(any(FoodCategory.class))).thenReturn(persistedFoodCategory);
        Mockito.when(FoodCategoryMapper.toDto(any(FoodCategory.class))).thenReturn(persistedFoodCategoryDto);
        assertEquals(1,foodCategoryService().save(FoodCategoryDto).getId());
    }

    @Test
    @DisplayName("Updating the FoodCategory entity")
    void update() {
        persistedFoodCategoryDto.setFoodCategory("newFoodCategoryName");
        Mockito.when(FoodCategoryRepository.save(any(FoodCategory.class))).thenReturn(persistedFoodCategory);
        Mockito.when(FoodCategoryMapper.toDto(any(FoodCategory.class))).thenReturn(persistedFoodCategoryDto);
        assertEquals("newFoodCategoryName",foodCategoryService().update(FoodCategoryDto).getFoodCategory());
    }

    @Test
    @DisplayName("Finding the FoodCategory entity by id")
    void findById() throws EntityNotFoundException {
        persistedFoodCategoryDto.setId(1);
        Mockito.when(FoodCategoryRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedFoodCategory));
        Mockito.when(FoodCategoryMapper.toDto(any(FoodCategory.class))).thenReturn(persistedFoodCategoryDto);
        FoodCategoryDto resultDto = foodCategoryService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}