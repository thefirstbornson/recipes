package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.service.NutritionalInformationService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientMapper extends AbstractMapper<IngredientDto, Ingredient> {
    private final ModelMapper mapper;
    private final NutritionalInformationService nutritionalInformationService;

    IngredientMapper(ModelMapper mapper, NutritionalInformationService nutritionalInformationService) {
        super(Ingredient.class, IngredientDto.class);
        this.mapper = mapper;
        this.nutritionalInformationService = nutritionalInformationService;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Ingredient.class, IngredientDto.class)
                .addMappings(m -> m.skip(IngredientDto::setNutritionalIdsAndAmountMap))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(IngredientDto.class, Ingredient.class)
                .addMappings(m -> m.skip(Ingredient::setIngredientNutritionalInformations))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Ingredient source, IngredientDto destination) {
        Map<Long,Float> nutritionalIdsAndAmountMap = new HashMap<>();
        source.getIngredientNutritionalInformations()
                .forEach(nutritionalInformation -> nutritionalIdsAndAmountMap.
                        put(nutritionalInformation.getNutrition().getId(), nutritionalInformation.getAmount()));
        destination.setNutritionalIdsAndAmountMap(nutritionalIdsAndAmountMap);
    }

    @Override
    void mapSpecificFields(IngredientDto source, Ingredient destination) {
        List<IngredientNutritionalInformation> ingredientNutritionalInformationList = new ArrayList<>();
        if (source.getNutritionalIdsAndAmountMap() !=null){
            List<Long>  nutritionalIds = new ArrayList<>(source.getNutritionalIdsAndAmountMap().keySet());
            List<NutritionalInformation> nutritionalInformationList = nutritionalInformationService.getAllEntitiesByIds(nutritionalIds);
            ingredientNutritionalInformationList = getIngredientNutritionalInformationList(source, nutritionalInformationList);
            ingredientNutritionalInformationList.
                    forEach(ingredientNutritionalInformation->ingredientNutritionalInformation.setIngredient(destination));
        }
        destination.setIngredientNutritionalInformations(new HashSet<>(ingredientNutritionalInformationList));
    }

    private List<IngredientNutritionalInformation> getIngredientNutritionalInformationList(IngredientDto source,
                                                                                           List<NutritionalInformation> nutritionalInformationList) {
        return nutritionalInformationList
                        .stream()
                        .map(n->new IngredientNutritionalInformation(
                                n, source.getNutritionalIdsAndAmountMap().get(n.getId())))
                        .collect(Collectors.toList());
    }
}
