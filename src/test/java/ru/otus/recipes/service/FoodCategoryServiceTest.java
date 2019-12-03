package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.dto.FoodCategoryDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.FoodCategoryRepository;
import ru.otus.recipes.service.mapper.FoodCategoryMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class FoodCategoryServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private FoodCategory persistedEntity;
    private FoodCategoryDto dto;
    private FoodCategoryDto persistedDto;

    @MockBean
    private FoodCategoryRepository repository;
    @MockBean
    private FoodCategoryMapper mapper;
    private FoodCategoryService service;

    @BeforeEach
    void setUp() {
        service = new  FoodCategoryService(repository, mapper);
        FoodCategory FoodCategory = new FoodCategory(0, "FoodCategoryName");
        dto = new FoodCategoryDto("FoodCategoryName");
        persistedEntity = new FoodCategory(ID,"FoodCategoryName");
        persistedDto = new FoodCategoryDto("FoodCategoryName");
        Mockito.when(mapper.toEntity(any(FoodCategoryDto.class))).thenReturn(FoodCategory);
    }

    @Test
    @DisplayName("Сохранение  FoodCategory entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(FoodCategory.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(FoodCategory.class))).thenReturn(persistedDto);
        assertEquals(1, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление foodCategory entity")
    void update() throws EntityNotFoundException {
        persistedDto.setFoodCategory("newFoodCategoryName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(FoodCategory.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(FoodCategory.class))).thenReturn(persistedDto);
        assertEquals("newFoodCategoryName", service.update(dto).getFoodCategory());
    }

    @Test
    @DisplayName("Поиск FoodCategory entity по id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(FoodCategory.class))).thenReturn(persistedDto);
        FoodCategoryDto resultDto = service.findById(1L);
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