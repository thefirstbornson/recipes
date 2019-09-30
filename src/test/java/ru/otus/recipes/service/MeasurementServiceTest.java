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
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MeasurementRepository;
import ru.otus.recipes.service.mapper.MeasurementMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class MeasurementServiceTest {

    private Measurement persistedMeasurement;
    private MeasurementDto MeasurementDto;
    private MeasurementDto persistedMeasurementDto;

    @MockBean
    private MeasurementRepository MeasurementRepository;
    @MockBean
    private MeasurementMapper MeasurementMapper;
    @Bean
    MeasurementService MeasurementService() {
        return new MeasurementService(MeasurementRepository, MeasurementMapper);
    }

    @BeforeEach
    void setUp() {
        Measurement Measurement = new Measurement(0, "MeasurementName");
        MeasurementDto = new MeasurementDto("MeasurementName");
        persistedMeasurement = new Measurement(1,"MeasurementName");
        persistedMeasurementDto = new MeasurementDto("MeasurementName");
        Mockito.when(MeasurementMapper.toEntity(any(MeasurementDto.class))).thenReturn(Measurement);
    }

    @Test
    @DisplayName("Saving the Measurement entity")
    void save() {
        persistedMeasurementDto.setId(1L);
        Mockito.when(MeasurementRepository.save(any(Measurement.class))).thenReturn(persistedMeasurement);
        Mockito.when(MeasurementMapper.toDto(any(Measurement.class))).thenReturn(persistedMeasurementDto);
        assertEquals(1,MeasurementService().save(MeasurementDto).getId());
    }

    @Test
    @DisplayName("Updating the Measurement entity")
    void update() {
        persistedMeasurement.setName("newMeasurementName");
        MeasurementDto persistedMeasurementDto = new MeasurementDto("newMeasurementName");
        Mockito.when(MeasurementRepository.save(any(Measurement.class))).thenReturn(persistedMeasurement);
        Mockito.when(MeasurementMapper.toDto(any(Measurement.class))).thenReturn(persistedMeasurementDto);
        assertEquals("newMeasurementName",MeasurementService().update(MeasurementDto).getName());
    }

    @Test
    @DisplayName("Finding the Measurement entity by id")
    void findById() throws EntityNotFoundException {
        persistedMeasurementDto.setId(1);
        Mockito.when(MeasurementRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedMeasurement));
        Mockito.when(MeasurementMapper.toDto(any(Measurement.class))).thenReturn(persistedMeasurementDto);
        MeasurementDto resultDto = MeasurementService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}