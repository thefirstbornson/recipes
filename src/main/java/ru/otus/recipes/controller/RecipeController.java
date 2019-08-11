package ru.otus.recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.domain.Ingredient;
import ru.otus.recipes.domain.Measurement;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.domain.RecipeIngredient;
import ru.otus.recipes.repository.*;
import ru.otus.recipes.service.RecipeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final MeasurementRepository measurementRepository;
    private final LevelRepository levelRepository;
    private final CuisineRepository cuisineRepository;

    @PersistenceContext
    EntityManager em;


    public RecipeController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                            RecipeIngredientRepository recipeIngredientRepository, RecipeService recipeService,
                            RecipeIngredientRepository recipeIngredientRepository1,  MeasurementRepository measurementRepository,
                            LevelRepository levelRepository, CuisineRepository cuisineRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
        this.recipeIngredientRepository = recipeIngredientRepository1;
        this.ingredientRepository = ingredientRepository;
        this.measurementRepository = measurementRepository;
        this.levelRepository = levelRepository;
        this.cuisineRepository = cuisineRepository;
    }

    @GetMapping("/recipes")
    public ResponseEntity<?> getAllRecipes() {
        //List<Recipe> recipes = recipeRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        List<Recipe> recipes =new ArrayList<>();
        if (!recipes.isEmpty()){
            return new ResponseEntity<>(recipes,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/recipes/{id}")
    public ResponseEntity<?> removeRecipe(@PathVariable("id") long id){
       // recipeIngredientRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
        return new ResponseEntity<>("{\"status\":\"deleted\"}", HttpStatus.OK);
    }

    @PutMapping(value="/recipes/{id}"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<?> editRecipe(@PathVariable("id") long id, @RequestBody Recipe deserializedRecipe){
        if (recipeRepository.findById(id).isPresent()){
            recipeRepository.save(deserializedRecipe);
            return (new ResponseEntity<>("{\"status\":\"updated\"}", HttpStatus.OK));
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value="/recipes"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<?> saveRecipe(@RequestBody Recipe deserializedRecipe){

        Set<RecipeIngredient>
                recipeIngredientSet = new HashSet<>(deserializedRecipe.getRecipeIngredients());
        deserializedRecipe.getRecipeIngredients().clear();
        deserializedRecipe.setLevel(levelRepository.getOne(deserializedRecipe.getLevel().getId()));
        deserializedRecipe.setCousine(cuisineRepository.getOne(deserializedRecipe.getCousine().getId()));
        recipeRepository.save(deserializedRecipe);
        System.out.println(em.contains(deserializedRecipe));

        for (RecipeIngredient recipeIngredient: recipeIngredientSet) {
            Ingredient ingredient = ingredientRepository.findById(recipeIngredient.getIngredient().getId()).get();
            Measurement measurement = measurementRepository.findById(recipeIngredient.getMeasurement().getId()).get();
            System.out.println(em.contains(ingredient));
            recipeIngredient.setRecipe(deserializedRecipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setMeasurement(measurement);
        }

        deserializedRecipe.setRecipeIngredients(recipeIngredientSet);
        recipeIngredientRepository.saveAll(recipeIngredientSet);

        //recipeService.createRecipe(deserializedRecipe);
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }
}
