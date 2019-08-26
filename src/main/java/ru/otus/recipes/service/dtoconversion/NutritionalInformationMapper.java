package ru.otus.recipes.service.dtoconversion;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.MeasurementDto;
import ru.otus.recipes.dto.NutritionalInformationDto;

@Service
public class NutritionalInformationMapper extends AbstractMapper<NutritionalInformationDto, NutritionalInformation> {
    NutritionalInformationMapper() {
        super( NutritionalInformation.class, NutritionalInformationDto.class);
    }
}
