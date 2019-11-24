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
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.LevelRepository;
import ru.otus.recipes.service.mapper.LevelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class LevelServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private static final Long DTO_ID_UPDATE =2L;
    private Level persistedLevel;
    private LevelDto LevelDto;
    private LevelDto persistedLevelDto;

    @MockBean
    private LevelRepository levelRepository;
    @MockBean
    private LevelMapper levelMapper;
    @Bean
    LevelService LevelService() {
        return new LevelService(levelRepository, levelMapper);
    }

    @BeforeEach
    void setUp() {
        Level Level = new Level(0);
        LevelDto = new LevelDto(0);
        persistedLevel = new Level(ID);
        persistedLevelDto = new LevelDto(DTO_ID);
        Mockito.when(levelMapper.toEntity(any(LevelDto.class))).thenReturn(Level);
    }

    @Test
    @DisplayName("Saving the Level entity")
    void save() throws EntityExistsException {
        persistedLevelDto.setId(DTO_ID);
        Mockito.when(levelRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(levelRepository.save(any(Level.class))).thenReturn(persistedLevel);
        Mockito.when(levelMapper.toDto(any(Level.class))).thenReturn(persistedLevelDto);
        assertEquals(1,LevelService().save(LevelDto).getId());
    }

    @Test
    @DisplayName("Updating the Level entity")
    void update() throws EntityNotFoundException {
        persistedLevelDto.setId(DTO_ID_UPDATE);
        Mockito.when(levelRepository.findById(anyLong())).thenReturn(Optional.of(persistedLevel));
        Mockito.when(levelRepository.save(any(Level.class))).thenReturn(persistedLevel);
        Mockito.when(levelMapper.toDto(any(Level.class))).thenReturn(persistedLevelDto);
        assertEquals(DTO_ID_UPDATE,LevelService().update(LevelDto).getId());
    }

    @Test
    @DisplayName("Finding the Level entity by id")
    void findById() throws EntityNotFoundException {
        Mockito.when(levelRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedLevel));
        Mockito.when(levelMapper.toDto(any(Level.class))).thenReturn(persistedLevelDto);
        LevelDto resultDto = LevelService().findById(ID);
        assertEquals(DTO_ID,resultDto.getId());
    }
}