package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.repository.MeasurementRepository;

@Service
public class MeasurementService extends AbstractService <Measurement,  MeasurementRepository>{
    public MeasurementService( MeasurementRepository repository) {
        super(repository);
    }
}
