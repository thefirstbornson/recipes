package ru.otus.recipes.service;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.repository.CuisineRepository;
import ru.otus.recipes.repository.LevelRepository;

@Service
public class LevelService extends AbstractService <Level,  LevelRepository>{
    public LevelService( LevelRepository repository) {
        super(repository);
    }
}
