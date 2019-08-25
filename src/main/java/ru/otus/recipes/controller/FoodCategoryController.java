package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.FoodCategory;
import ru.otus.recipes.dto.FoodCategoryDto;
import ru.otus.recipes.service.FoodCategoryService;

@RestController
@RequestMapping("/foodcategories")
public class FoodCategoryController extends AbstractController<FoodCategory, FoodCategoryService, FoodCategoryDto> {
    public FoodCategoryController(FoodCategoryService service) {
        super(service, FoodCategory.class);
    }
}