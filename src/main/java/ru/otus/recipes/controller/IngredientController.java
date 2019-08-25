package ru.otus.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.dto.IngredientDto;
import ru.otus.recipes.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController extends AbstractController<Ingredient, IngredientService, IngredientDto>{

    protected IngredientController(IngredientService service) {
        super(service, Ingredient.class);
    }

    @Override
    public ResponseEntity<List<IngredientDto>> getAll() {
        List<Ingredient> recipes = super.getService().findAll();
        List<IngredientDto> recipeDtoList =recipes.stream().map(conversionDtoServcie::convertToDto).collect(Collectors.toList());
        return  ResponseEntity.ok(recipeDtoList);
}
