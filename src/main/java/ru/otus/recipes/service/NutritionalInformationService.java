package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.repository.MeasurementRepository;
import ru.otus.recipes.repository.NutritionalInformationRepository;

@Service
public class NutritionalInformationService extends AbstractService <NutritionalInformation, NutritionalInformationRepository>{
    public NutritionalInformationService( NutritionalInformationRepository repository) {
        super(repository);
    }
}
