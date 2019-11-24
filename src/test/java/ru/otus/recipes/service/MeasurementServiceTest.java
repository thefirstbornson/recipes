package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.dto.MeasurementDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MeasurementRepository;
import ru.otus.recipes.service.mapper.MeasurementMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MeasurementServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Measurement persistedMeasurement;
    private MeasurementDto MeasurementDto;
    private MeasurementDto persistedMeasurementDto;

    @MockBean
    private MeasurementRepository measurementRepository;
    @MockBean
    private MeasurementMapper measurementMapper;
    private MeasurementService measurementService;

    @BeforeEach
    void setUp() {
        measurementService = new MeasurementService(measurementRepository, measurementMapper);
        Measurement Measurement = new Measurement(0, "MeasurementName");
        MeasurementDto = new MeasurementDto("MeasurementName");
        persistedMeasurement = new Measurement(ID,"MeasurementName");
        persistedMeasurementDto = new MeasurementDto("MeasurementName");
        Mockito.when(measurementMapper.toEntity(any(MeasurementDto.class))).thenReturn(Measurement);
    }

    @Test
    @DisplayName("Saving the Measurement entity")
    void save() throws EntityExistsException {
        persistedMeasurementDto.setId(DTO_ID);
        Mockito.when(measurementRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(measurementRepository.save(any(Measurement.class))).thenReturn(persistedMeasurement);
        Mockito.when(measurementMapper.toDto(any(Measurement.class))).thenReturn(persistedMeasurementDto);
        assertEquals(1,measurementService.save(MeasurementDto).getId());
    }

    @Test
    @DisplayName("Updating the Measurement entity")
    void update() throws EntityNotFoundException {
        persistedMeasurement.setName("newMeasurementName");
        MeasurementDto persistedMeasurementDto = new MeasurementDto("newMeasurementName");
        Mockito.when(measurementRepository.findById(anyLong())).thenReturn(Optional.of(persistedMeasurement));
        Mockito.when(measurementRepository.save(any(Measurement.class))).thenReturn(persistedMeasurement);
        Mockito.when(measurementMapper.toDto(any(Measurement.class))).thenReturn(persistedMeasurementDto);
        assertEquals("newMeasurementName",measurementService.update(MeasurementDto).getName());
    }

    @Test
    @DisplayName("Finding the Measurement entity by id")
    void findById() throws EntityNotFoundException {
        persistedMeasurementDto.setId(DTO_ID);
        Mockito.when(measurementRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedMeasurement));
        Mockito.when(measurementMapper.toDto(any(Measurement.class))).thenReturn(persistedMeasurementDto);
        MeasurementDto resultDto = measurementService.findById(ID);
        assertEquals(DTO_ID,resultDto.getId());
    }
}