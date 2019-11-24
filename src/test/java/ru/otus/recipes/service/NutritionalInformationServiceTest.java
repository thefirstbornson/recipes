package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.NutritionalInformationDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.NutritionalInformationRepository;
import ru.otus.recipes.service.mapper.NutritionalInformationMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class NutritionalInformationServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private NutritionalInformation persistedNutritionalInformation;
    private NutritionalInformationDto NutritionalInformationDto;
    private NutritionalInformationDto persistedNutritionalInformationDto;

    @MockBean
    private NutritionalInformationRepository nutritionalInformationRepository;
    @MockBean
    private NutritionalInformationMapper nutritionalInformationMapper;
    NutritionalInformationService nutritionalInformationService;

    @BeforeEach
    void setUp() {
        nutritionalInformationService =
                new NutritionalInformationService(nutritionalInformationRepository, nutritionalInformationMapper);
        NutritionalInformation NutritionalInformation = new NutritionalInformation("NutritionalInformationName");
        NutritionalInformationDto = new NutritionalInformationDto("NutritionalInformationName");
        persistedNutritionalInformation = new NutritionalInformation("NutritionalInformationName");
        persistedNutritionalInformationDto = new NutritionalInformationDto("NutritionalInformationName");
        Mockito.when(nutritionalInformationMapper.toEntity(any(NutritionalInformationDto.class))).thenReturn(NutritionalInformation);
    }

    @Test
    @DisplayName("Saving the NutritionalInformation entity")
    void save() throws EntityExistsException {
        persistedNutritionalInformationDto.setId(1L);
        Mockito.when(nutritionalInformationRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(nutritionalInformationRepository.save(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformation);
        Mockito.when(nutritionalInformationMapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformationDto);
        assertEquals(1,nutritionalInformationService.save(NutritionalInformationDto).getId());
    }

    @Test
    @DisplayName("Updating the NutritionalInformation entity")
    void update() throws EntityNotFoundException {
        persistedNutritionalInformation.setNutrition("newNutritionalInformationName");
        NutritionalInformationDto persistedNutritionalInformationDto = new NutritionalInformationDto("newNutritionalInformationName");
        Mockito.when(nutritionalInformationRepository.findById(anyLong())).thenReturn(Optional.of(persistedNutritionalInformation));
        Mockito.when(nutritionalInformationRepository.save(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformation);
        Mockito.when(nutritionalInformationMapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformationDto);
        assertEquals("newNutritionalInformationName",nutritionalInformationService.update(NutritionalInformationDto).getNutrition());
    }

    @Test
    @DisplayName("Finding the NutritionalInformation entity by id")
    void findById() throws EntityNotFoundException {
        persistedNutritionalInformationDto.setId(1);
        Mockito.when(nutritionalInformationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedNutritionalInformation));
        Mockito.when(nutritionalInformationMapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformationDto);
        NutritionalInformationDto resultDto = nutritionalInformationService.findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}