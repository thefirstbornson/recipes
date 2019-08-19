package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.service.CourseService;
import ru.otus.recipes.service.CuisineService;

@RestController
@RequestMapping("/cuisines")
public class CuisineController extends AbstractController<Cuisine, CuisineService> {
    public CuisineController(CuisineService service) {
        super(service);
    }
}