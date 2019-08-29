package ru.otus.recipes.service.mapper;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.dto.LevelDto;

@Service
public class LevelMapper extends AbstractMapper<LevelDto, Level> {
    LevelMapper() {
        super(Level.class, LevelDto.class);
    }
}
