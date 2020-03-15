package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.dto.IngredientNutritionalDto;
import ru.otus.recipes.dto.NutritionalInformationDto;
import ru.otus.recipes.service.IngredientNutritionalInformationService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientMapper extends AbstractMapper<IngredientDto, Ingredient> {
    private final ModelMapper mapper;
    private final IngredientNutritionalMapper ingredientNutritionalMapper;
    private final IngredientNutritionalInformationService ingredientNutritionalInformationService;

    IngredientMapper(ModelMapper mapper, IngredientNutritionalMapper ingredientNutritionalMapper,
                     IngredientNutritionalInformationService ingredientNutritionalInformationService) {
        super(Ingredient.class, IngredientDto.class);
        this.mapper = mapper;
        this.ingredientNutritionalMapper = ingredientNutritionalMapper;
        this.ingredientNutritionalInformationService = ingredientNutritionalInformationService;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Ingredient.class, IngredientDto.class)
                .addMappings(m -> m.skip(IngredientDto::setNutritionalInformation))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(IngredientDto.class, Ingredient.class)
                .addMappings(m -> m.skip(Ingredient::setIngredientNutritionalInformations))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Ingredient source, IngredientDto destination) {
        destination.setNutritionalInformation(source.getIngredientNutritionalInformations()
                .stream()
                .map(ingredientNutritionalMapper::toDto)
                .collect(Collectors.toList())
        );
    }

    @Override
    void mapSpecificFields(IngredientDto source, Ingredient destination) {
        List<IngredientNutritionalInformation> ingredientNutritionalInformations = Collections.emptyList();
        if (source.getNutritionalInformation()!=null) {
            ingredientNutritionalInformations = ingredientNutritionalInformationService
                    .getAllEntitiesByIds(source.getNutritionalInformation()
                        .stream()
                        .map(IngredientNutritionalDto::getId)
                        .collect(Collectors.toList()));
        destination.setIngredientNutritionalInformations(new HashSet<>(ingredientNutritionalInformations));
    }
    }

}
