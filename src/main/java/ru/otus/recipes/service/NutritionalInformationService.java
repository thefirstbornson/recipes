package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.NutritionalInformationDto;
import ru.otus.recipes.repository.NutritionalInformationRepository;
import ru.otus.recipes.service.dtoconversion.NutritionalInformationMapper;

@Service
public class NutritionalInformationService extends AbstractService <NutritionalInformationDto, NutritionalInformation,
        NutritionalInformationRepository, NutritionalInformationMapper>{
    @Autowired
    public NutritionalInformationService( NutritionalInformationRepository repository, NutritionalInformationMapper nutritionalInformationMapper) {
        super(repository, nutritionalInformationMapper);
    }
}

