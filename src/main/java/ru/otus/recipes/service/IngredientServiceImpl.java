package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.IngredientNutritionalInformation;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.repository.IngredientRepository;
import ru.otus.recipes.repository.NutritionalInformationRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class IngredientServiceImpl implements IngredientService{
    private final NutritionalInformationRepository nutritionalInformationRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(NutritionalInformationRepository nutritionalInformationRepository, IngredientRepository ingredientRepository) {
        this.nutritionalInformationRepository = nutritionalInformationRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient createIngredient(IngredientDto ingredientDto) {
        List<Long> nutritionalIds = ingredientDto.getNutritionalIdsndAmountMap().keySet()
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<NutritionalInformation> nutritionalInformationList = nutritionalInformationRepository.findByIdIn(nutritionalIds);
        List<IngredientNutritionalInformation> ingredientNutritionalInformationList = nutritionalInformationList
                .stream()
                .map(n->new IngredientNutritionalInformation(
                        n, Integer.parseInt(ingredientDto.getNutritionalIdsndAmountMap().get(String.valueOf(n.getId())))))
                .collect(Collectors.toList());
        Ingredient ingredient = new Ingredient(ingredientDto.getName(),ingredientNutritionalInformationList);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient findIngredientById(long id) {
        return ingredientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Ingredient> findAllIngredients(List<Long> ingredientIds) {
        return ingredientRepository.findByIdIn(ingredientIds);
    }
}
