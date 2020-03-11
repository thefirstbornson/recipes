package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.dto.IngredientNutritionalDto;

import javax.annotation.PostConstruct;

@Service
public class IngredientNutritionalMapper extends AbstractMapper<IngredientNutritionalDto, IngredientNutritionalInformation> {
    private final ModelMapper mapper;
    private final NutritionalInformationMapper nutritionalInformationMapper;

    IngredientNutritionalMapper(ModelMapper mapper, NutritionalInformationMapper nutritionalInformationMapper) {
        super(IngredientNutritionalInformation.class, IngredientNutritionalDto.class);
        this.mapper = mapper;
        this.nutritionalInformationMapper = nutritionalInformationMapper;
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
        destination.setNutrition(nutritionalInformationMapper.toEntity(source.getNutrition()));
    }
}
