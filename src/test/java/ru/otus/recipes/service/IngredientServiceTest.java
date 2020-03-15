package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.IngredientNutritionalInformationRepository;
import ru.otus.recipes.repository.IngredientRepository;
import ru.otus.recipes.service.mapper.IngredientMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class IngredientServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Ingredient persistedEntity;
    private ru.otus.recipes.dto.IngredientDto dto;
    private IngredientDto persistedDto;

    @MockBean
    private IngredientRepository repository;
    @MockBean
    private IngredientMapper mapper;
    @MockBean
    private IngredientNutritionalInformationRepository ingredientNutritionalInformationRepository;
    private IngredientService service;

    @BeforeEach

    void setUp() {
        service = new IngredientService(repository, ingredientNutritionalInformationRepository, mapper);
        Ingredient ingredient = new Ingredient( "IngredientName", Collections.singletonList(new IngredientNutritionalInformation()));
        dto = new IngredientDto("IngredientName", new ArrayList<>());
        persistedEntity = new Ingredient("IngredientName", Collections.singletonList(new IngredientNutritionalInformation()));
        persistedDto = new IngredientDto("IngredientName",new ArrayList<>());
        Mockito.when(mapper.toEntity(any(IngredientDto.class))).thenReturn(ingredient);
    }

    @Test
    @DisplayName("Сохранение  Ingredient entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(Ingredient.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Ingredient.class))).thenReturn(persistedDto);
        assertEquals(1, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление ingredient entity")
    void update() throws EntityNotFoundException {
        persistedDto.setName("newIngredientName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Ingredient.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Ingredient.class))).thenReturn(persistedDto);
        assertEquals("newIngredientName", service.update(dto).getName());
    }

    @Test
    @DisplayName("Поиск Ingredient entity by id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Ingredient.class))).thenReturn(persistedDto);
        IngredientDto resultDto = service.findById(1L);
        assertEquals(DTO_ID,resultDto.getId());
    }

    @Test
    @DisplayName("Ошибка при сохранении существующей entity")
    void saveEntityExistsException() {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        assertThrows(EntityExistsException.class, () -> service.save(dto));
    }

    @Test
    @DisplayName("Ошибка при обновлении несуществующей entity")
    void updateEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("Ошибка при поиске несуществующей entity")
    void findByIdEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(DTO_ID));
    }

    @Test
    @DisplayName("Ошибка при удалении несуществующей entity")
    void deleteByIdEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteById(DTO_ID));
    }
}