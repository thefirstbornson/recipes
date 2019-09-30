package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.dto.LevelDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class LevelMapperTest {

    private final Level level = new Level(0);
    private final LevelDto levelDto = new LevelDto(0);

    @Autowired
    private LevelMapper levelMapper;

    @Test
    void toEntity() {
        Level levelFromDto = levelMapper.toEntity(levelDto);
        assertEquals(levelFromDto.getId(), this.level.getId());
    }

    @Test
    void toDto() {
        LevelDto levelDtoFromEntity = levelMapper.toDto(level);
        assertEquals(levelDtoFromEntity.getId(), level.getId());
    }
}