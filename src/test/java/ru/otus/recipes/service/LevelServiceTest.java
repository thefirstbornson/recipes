package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.dto.LevelDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.LevelRepository;
import ru.otus.recipes.service.mapper.LevelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class LevelServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private static final Long DTO_ID_UPDATE =2L;
    private Level persistedEntity;
    private LevelDto dto;
    private LevelDto persistedDto;

    @MockBean
    private LevelRepository repository;
    @MockBean
    private LevelMapper mapper;
    private LevelService service;

    @BeforeEach
    void setUp() {
        service = new LevelService(repository, mapper);
        Level Level = new Level(0);
        dto = new LevelDto(0);
        persistedEntity = new Level(ID);
        persistedDto = new LevelDto(DTO_ID);
        Mockito.when(mapper.toEntity(any(LevelDto.class))).thenReturn(Level);
    }

    @Test
    @DisplayName("Сохранение  Level entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(Level.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Level.class))).thenReturn(persistedDto);
        assertEquals(1, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление level entity")
    void update() throws EntityNotFoundException {
        persistedDto.setId(DTO_ID_UPDATE);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Level.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Level.class))).thenReturn(persistedDto);
        assertEquals(DTO_ID_UPDATE, service.update(dto).getId());
    }

    @Test
    @DisplayName("Поиск Level entity по id")
    void findById() throws EntityNotFoundException {
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Level.class))).thenReturn(persistedDto);
        LevelDto resultDto = service.findById(ID);
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