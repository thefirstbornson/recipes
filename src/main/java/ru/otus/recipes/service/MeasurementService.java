package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.dto.MeasurementDto;
import ru.otus.recipes.repository.MeasurementRepository;
import ru.otus.recipes.service.mapper.MeasurementMapper;

@Service
public class MeasurementService extends AbstractService <MeasurementDto, Measurement,  MeasurementRepository, MeasurementMapper>{
    @Autowired
    public MeasurementService( MeasurementRepository repository, MeasurementMapper measurementMapper) {
        super(repository, measurementMapper, Measurement.class);
    }
}
