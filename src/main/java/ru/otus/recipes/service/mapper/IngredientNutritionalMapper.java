package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.dto.IngredientNutritionalDto;
import ru.otus.recipes.service.NutritionalInformationService;

import javax.annotation.PostConstruct;

@Service
public class IngredientNutritionalMapper extends AbstractMapper<IngredientNutritionalDto, IngredientNutritionalInformation> {
    private final ModelMapper mapper;
    private final NutritionalInformationMapper nutritionalInformationMapper;
    private final NutritionalInformationService nutritionalInformationService;

    IngredientNutritionalMapper(ModelMapper mapper, NutritionalInformationMapper nutritionalInformationMapper, NutritionalInformationService nutritionalInformationService) {
        super(IngredientNutritionalInformation.class, IngredientNutritionalDto.class);
        this.mapper = mapper;
        this.nutritionalInformationMapper = nutritionalInformationMapper;
        this.nutritionalInformationService = nutritionalInformationService;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(IngredientNutritionalInformation.class, IngredientNutritionalDto.class)
                .addMappings(m -> m.skip(IngredientNutritionalDto::setNutrition))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(IngredientNutritionalDto.class, IngredientNutritionalInformation.class)
                .addMappings(m -> m.skip(IngredientNutritionalInformation::setNutrition))
                .setPostConverter(toEntityConverter());
    }


    @Override
    void mapSpecificFields(IngredientNutritionalInformation source, IngredientNutritionalDto destination) {
        destination.setNutrition(nutritionalInformationMapper.toDto(source.getNutrition()));
    }

    @Override
    void mapSpecificFields(IngredientNutritionalDto source, IngredientNutritionalInformation destination) {
        destination.setNutrition(nutritionalInformationService.getEntityById(source.getNutrition().getId()));
    }
}
