package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Level;
import ru.otus.recipes.dto.LevelDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.LevelRepository;
import ru.otus.recipes.service.mapper.LevelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class LevelServiceTest {

    private Level persistedLevel;
    private LevelDto LevelDto;
    private LevelDto persistedLevelDto;

    @MockBean
    private LevelRepository LevelRepository;
    @MockBean
    private LevelMapper LevelMapper;
    @Bean
    LevelService LevelService() {
        return new LevelService(LevelRepository, LevelMapper);
    }

    @BeforeEach
    void setUp() {
        Level Level = new Level(0);
        LevelDto = new LevelDto(0);
        persistedLevel = new Level(1);
        persistedLevelDto = new LevelDto(1);
        Mockito.when(LevelMapper.toEntity(any(LevelDto.class))).thenReturn(Level);
    }

    @Test
    @DisplayName("Saving the Level entity")
    void save() {
        persistedLevelDto.setId(1L);
        Mockito.when(LevelRepository.save(any(Level.class))).thenReturn(persistedLevel);
        Mockito.when(LevelMapper.toDto(any(Level.class))).thenReturn(persistedLevelDto);
        assertEquals(1,LevelService().save(LevelDto).getId());
    }

    @Test
    @DisplayName("Updating the Level entity")
    void update() {
        persistedLevelDto.setId(2);
        Mockito.when(LevelRepository.save(any(Level.class))).thenReturn(persistedLevel);
        Mockito.when(LevelMapper.toDto(any(Level.class))).thenReturn(persistedLevelDto);
        assertEquals(2,LevelService().update(LevelDto).getId());
    }

    @Test
    @DisplayName("Finding the Level entity by id")
    void findById() throws EntityNotFoundException {
        Mockito.when(LevelRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedLevel));
        Mockito.when(LevelMapper.toDto(any(Level.class))).thenReturn(persistedLevelDto);
        LevelDto resultDto = LevelService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}