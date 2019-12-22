package ru.otus.recipes.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.dto.LevelDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.h2.console.enabled=true"})
@TestPropertySource("classpath:application-test.properties")
@Transactional
class LevelMapperTest {

    private final Level level = new Level(0L);
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