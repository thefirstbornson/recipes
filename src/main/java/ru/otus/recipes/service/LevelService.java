package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.dto.LevelDto;
import ru.otus.recipes.repository.LevelRepository;
import ru.otus.recipes.service.dtoconversion.LevelMapper;

@Service
public class LevelService extends AbstractService <LevelDto,Level,  LevelRepository, LevelMapper>{
    @Autowired
    public LevelService( LevelRepository repository, LevelMapper levelMapper) {
        super(repository, levelMapper);
    }
}
