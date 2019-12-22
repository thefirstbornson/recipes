package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.dto.MeasurementDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MeasurementRepository;
import ru.otus.recipes.service.mapper.MeasurementMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MeasurementServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Measurement persistedEntity;
    private MeasurementDto dto;
    private MeasurementDto persistedDto;

    @MockBean
    private MeasurementRepository repository;
    @MockBean
    private MeasurementMapper mapper;
    private MeasurementService service;

    @BeforeEach
    void setUp() {
        service = new MeasurementService(repository, mapper);
        Measurement Measurement = new Measurement(0L, "MeasurementName");
        dto = new MeasurementDto("MeasurementName");
        persistedEntity = new Measurement(ID,"MeasurementName");
        persistedDto = new MeasurementDto("MeasurementName");
        Mockito.when(mapper.toEntity(any(MeasurementDto.class))).thenReturn(Measurement);
    }

    @Test
    @DisplayName("Сохранение  Measurement entity")
    void save() throws EntityExistsException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(Measurement.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Measurement.class))).thenReturn(persistedDto);
        assertEquals(1, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление measurement entity")
    void update() throws EntityNotFoundException {
        persistedEntity.setName("newMeasurementName");
        MeasurementDto persistedMeasurementDto = new MeasurementDto("newMeasurementName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(Measurement.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(Measurement.class))).thenReturn(persistedMeasurementDto);
        assertEquals("newMeasurementName", service.update(dto).getName());
    }

    @Test
    @DisplayName("Поиск Measurement entity по id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(Measurement.class))).thenReturn(persistedDto);
        MeasurementDto resultDto = service.findById(ID);
        assertEquals(DTO_ID,resultDto.getId());
    }

    @Test
    @DisplayName("Ошибка при сохранении существующей entity")
    void saveEntityExistsException() {
        dto.setId(DTO_ID);
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