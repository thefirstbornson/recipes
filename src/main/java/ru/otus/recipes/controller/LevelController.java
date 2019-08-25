package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.dto.LevelDto;
import ru.otus.recipes.service.LevelService;

@RestController
@RequestMapping("/levels")
public class LevelController extends AbstractController<Level, LevelService, LevelDto> {
    public LevelController(LevelService service) {
        super(service, Level.class);
    }
}