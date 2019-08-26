package ru.otus.recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.service.dtoconversion.Mapper;
import ru.otus.recipes.service.RecipeService;

import java.util.*;
import java.util.stream.Collectors;


@RestController
public class RecipeController {
    private final RecipeService recipeService;
    private final ConversionDtoServcie conversionDtoServcie;

    public RecipeController(RecipeService recipeService, ConversionDtoServcie conversionDtoServcie) {
        this.recipeService = recipeService;
        this.conversionDtoServcie = conversionDtoServcie;
    }

}
