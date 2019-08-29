package ru.otus.recipes.service.mapper;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.NutritionalInformationDto;

@Service
public class NutritionalInformationMapper extends AbstractMapper<NutritionalInformationDto, NutritionalInformation> {
    NutritionalInformationMapper() {
        super( NutritionalInformation.class, NutritionalInformationDto.class);
    }
}
