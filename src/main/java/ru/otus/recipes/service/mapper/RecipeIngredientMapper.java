package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.RecipeIngredient;
import ru.otus.recipes.dto.RecipeIngredientDto;
import ru.otus.recipes.service.IngredientService;
import ru.otus.recipes.service.MeasurementService;

import javax.annotation.PostConstruct;

@Service
public class RecipeIngredientMapper extends AbstractMapper<RecipeIngredientDto, RecipeIngredient> {
    private final ModelMapper mapper;
    private final MeasurementMapper measurementMapper;
    private final IngredientMapper ingredientMapper;
    private final IngredientService ingredientService;
    private final MeasurementService measurementService;

    RecipeIngredientMapper(ModelMapper mapper, MeasurementMapper measurementMapper, IngredientMapper ingredientMapper, IngredientService ingredientService, MeasurementService measurementService) {
        super(RecipeIngredient.class, RecipeIngredientDto.class);
        this.mapper = mapper;
        this.measurementMapper = measurementMapper;
        this.ingredientMapper = ingredientMapper;
        this.ingredientService = ingredientService;
        this.measurementService = measurementService;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(RecipeIngredient.class, RecipeIngredientDto.class)
                .addMappings(m -> m.skip(RecipeIngredientDto::setIngredient))
                .addMappings(m -> m.skip(RecipeIngredientDto::setMeasurement))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(RecipeIngredientDto.class, RecipeIngredient.class)
                .addMappings(m -> m.skip(RecipeIngredient::setIngredient))
                .addMappings(m -> m.skip(RecipeIngredient::setMeasurement))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(RecipeIngredient source, RecipeIngredientDto destination) {
        destination.setIngredient(ingredientMapper.toDto(source.getIngredient()));
        destination.setMeasurement(measurementMapper.toDto(source.getMeasurement()));
    }

    @Override
    void mapSpecificFields(RecipeIngredientDto source, RecipeIngredient destination) {
        destination.setIngredient(ingredientService.getEntityById(source.getIngredient().getId()));
        destination.setMeasurement(measurementService.getEntityById(source.getMeasurement().getId()));
    }

}
