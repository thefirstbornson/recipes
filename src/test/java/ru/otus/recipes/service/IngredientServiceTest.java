package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.IngredientNutritionalInformationRepository;
import ru.otus.recipes.repository.IngredientRepository;
import ru.otus.recipes.service.mapper.IngredientMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class IngredientServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Ingredient persistedIngredient;
    private ru.otus.recipes.dto.IngredientDto IngredientDto;
    private IngredientDto persistedIngredientDto;

    @MockBean
    private IngredientRepository ingredientRepository;
    @MockBean
    private IngredientMapper ingredientMapper;
    @MockBean
    private IngredientNutritionalInformationRepository ingredientNutritionalInformationRepository;
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        ingredientService= new IngredientService(ingredientRepository, ingredientNutritionalInformationRepository, ingredientMapper);
        Ingredient ingredient = new Ingredient( "IngredientName", Collections.singletonList(new IngredientNutritionalInformation()));
        IngredientDto = new IngredientDto("IngredientName", new HashMap<>());
        persistedIngredient = new Ingredient("IngredientName", Collections.singletonList(new IngredientNutritionalInformation()));
        persistedIngredientDto = new IngredientDto("IngredientName",new HashMap<>());
        Mockito.when(ingredientMapper.toEntity(any(IngredientDto.class))).thenReturn(ingredient);
    }

    @Test
    @DisplayName("Saving the Ingredient entity")
    void save() throws EntityExistsException {
        persistedIngredientDto.setId(DTO_ID);
        Mockito.when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(ingredientRepository.save(any(Ingredient.class))).thenReturn(persistedIngredient);
        Mockito.when(ingredientMapper.toDto(any(Ingredient.class))).thenReturn(persistedIngredientDto);
        assertEquals(1,ingredientService.save(IngredientDto).getId());
    }

    @Test
    @DisplayName("Updating the Ingredient entity")
    void update() throws EntityNotFoundException {
        persistedIngredientDto.setName("newIngredientName");
        Mockito.when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(persistedIngredient));
        Mockito.when(ingredientRepository.save(any(Ingredient.class))).thenReturn(persistedIngredient);
        Mockito.when(ingredientMapper.toDto(any(Ingredient.class))).thenReturn(persistedIngredientDto);
        assertEquals("newIngredientName",ingredientService.update(IngredientDto).getName());
    }

    @Test
    @DisplayName("Finding the Ingredient entity by id")
    void findById() throws EntityNotFoundException {
        persistedIngredientDto.setId(DTO_ID);
        Mockito.when(ingredientRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedIngredient));
        Mockito.when(ingredientMapper.toDto(any(Ingredient.class))).thenReturn(persistedIngredientDto);
        IngredientDto resultDto = ingredientService.findById(1L);
        assertEquals(DTO_ID,resultDto.getId());
    }
}