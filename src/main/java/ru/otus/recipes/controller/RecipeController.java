package ru.otus.recipes.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.RecipeDto;
import ru.otus.recipes.service.ConversionDtoServcie;
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

    @GetMapping("/recipes")
    public ResponseEntity<?> getAllRecipes() {
        List<Recipe> recipes = recipeService.findAllRecipes();
            List<RecipeDto> recipeDtoList =recipes.stream().map(conversionDtoServcie::convertToDto).collect(Collectors.toList());
            return new ResponseEntity<>(recipeDtoList,HttpStatus.OK);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable("id") long id) {
        RecipeDto recipeDto = conversionDtoServcie.convertToDto(recipeService.findRecipeById(id));
        return new ResponseEntity<>(recipeDto,HttpStatus.OK);
    }

    @DeleteMapping(value="/recipes/{id}")
    public ResponseEntity<?> removeRecipe(@PathVariable("id") long id){
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>("{\"status\":\"deleted\"}", HttpStatus.OK);
    }

    @PutMapping(value="/recipes/{id}")
    @ResponseBody
    public ResponseEntity<?> editRecipe(@PathVariable("id") long id, @RequestBody RecipeDto deserializedRecipe){
        Recipe recipe =  recipeService.updateRecipe(deserializedRecipe);
            return (new ResponseEntity<>("{\"status\":\"updated\"}", HttpStatus.OK));
    }

    @PostMapping(value="/recipes")
    @ResponseBody
    public ResponseEntity<?> saveRecipe(@RequestBody RecipeDto deserializedRecipe){
        Recipe recipe = recipeService.createRecipe(deserializedRecipe);
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleServerException(EmptyResultDataAccessException e){
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
