package ru.otus.recipes.service.dtoconversion;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.repository.NutritionalInformationRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientMapper extends AbstractMapper<IngredientDto, Ingredient> {
    private final ModelMapper mapper;
    private final NutritionalInformationRepository nutritionalInformationRepository;

    IngredientMapper(ModelMapper mapper, NutritionalInformationRepository nutritionalInformationRepository) {
        super(Ingredient.class, IngredientDto.class);
        this.mapper = mapper;
        this.nutritionalInformationRepository = nutritionalInformationRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Ingredient.class, IngredientDto.class)
                .addMappings(m -> m.skip(IngredientDto::setNutritionalIdsndAmountMap))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(IngredientDto.class, Ingredient.class)
                .addMappings(m -> m.skip(Ingredient::setIngredientNutritionalInformations))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Ingredient source, IngredientDto destination) {
        Map<Long,Integer> nutritionalIdsndAmountMap = new HashMap<>();
        source.getIngredientNutritionalInformations()
                .forEach(nutritionalInformation -> nutritionalIdsndAmountMap.
                        put(nutritionalInformation.getNutrition().getId(), nutritionalInformation.getAmount()));
        destination.setNutritionalIdsndAmountMap(nutritionalIdsndAmountMap);
    }

    @Override
    void mapSpecificFields(IngredientDto source, Ingredient destination) {
        List<Long> nutritionalIds = new ArrayList<>(source.getNutritionalIdsndAmountMap().keySet());
        List<NutritionalInformation> nutritionalInformationList = nutritionalInformationRepository.findByIdIn(nutritionalIds);
        List<IngredientNutritionalInformation> ingredientNutritionalInformationList = nutritionalInformationList
                .stream()
                .map(n->new IngredientNutritionalInformation(
                        n, source.getNutritionalIdsndAmountMap().get(n.getId())))
                .collect(Collectors.toList());
        for(IngredientNutritionalInformation ingredientNutritionalInformation : ingredientNutritionalInformationList) {
            ingredientNutritionalInformation.setIngredient(destination);
        }
        destination.setIngredientNutritionalInformations(new HashSet<>(ingredientNutritionalInformationList));
    }
}
