package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.dto.CuisineDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.CuisineRepository;
import ru.otus.recipes.service.mapper.CuisineMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class CuisineServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Cuisine persistedEntity;
    private CuisineDto dto;
    private CuisineDto persistedDto;

    @MockBean
    private CuisineRepository repository;
    @MockBean
    private CuisineMapper mapper;
    private CuisineService service;

    @BeforeEach
    void setUp() {
        service =new CuisineService(repository, mapper);
        Cuisine cuisine = new Cuisine(0, "CuisineName");
        dto = new CuisineDto("CuisineName");
        persistedEntity = new Cuisine(ID,"CuisineName");
        persistedDto = new CuisineDto("CuisineName");
        Mockito.when(mapper.toEntity(any(CuisineDto.class))).thenReturn(cuisine);
    }

    @Test
    @DisplayName("Сохранение  Cuisine entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(Cuisine.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Cuisine.class))).thenReturn(persistedDto);
        assertEquals(DTO_ID, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление cuisine entity")
    void update() throws EntityNotFoundException {
        Cuisine persistedCuisine = new Cuisine(1,"CuisineName");
        persistedCuisine.setCuisine("newCuisineName");
        persistedDto.setCuisine("newCuisineName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedCuisine));
        Mockito.when(repository.save(any(Cuisine.class))).thenReturn(persistedCuisine);
        Mockito.when(mapper.toDto(any(Cuisine.class))).thenReturn(persistedDto);
        assertEquals("newCuisineName", service.update(dto).getCuisine());
    }

    @Test
    @DisplayName("Поиск Cuisine entity по id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Cuisine.class))).thenReturn(persistedDto);
        CuisineDto resultDto = service.findById(1L);
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
