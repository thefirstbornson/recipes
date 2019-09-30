package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.dto.MeasurementDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class MeasurementMapperTest {

    private final Measurement measurement = new Measurement(0, "measurementName");
    private final MeasurementDto measurementDto = new MeasurementDto("measurementName");

    @Autowired
    private MeasurementMapper measurementMapper;

    @Test
    void toEntity() {
        Measurement measurementFromDto = measurementMapper.toEntity(measurementDto);
        assertEquals(measurementFromDto.getId(), this.measurement.getId());
    }

    @Test
    void toDto() {
        MeasurementDto measurementDtoFromEntity = measurementMapper.toDto(measurement);
        assertEquals(measurementDtoFromEntity.getName(), measurement.getName());
    }
}