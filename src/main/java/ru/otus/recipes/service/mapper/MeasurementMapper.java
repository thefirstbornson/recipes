package ru.otus.recipes.service.mapper;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.dto.MeasurementDto;

@Service
public class MeasurementMapper extends AbstractMapper<MeasurementDto, Measurement> {
    MeasurementMapper() {
        super( Measurement.class,  MeasurementDto.class);
    }
}
