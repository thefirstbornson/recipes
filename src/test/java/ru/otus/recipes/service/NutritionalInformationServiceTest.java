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
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.NutritionalInformationRepository;
import ru.otus.recipes.service.mapper.NutritionalInformationMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class NutritionalInformationServiceTest {

    private NutritionalInformation persistedNutritionalInformation;
    private NutritionalInformationDto NutritionalInformationDto;
    private NutritionalInformationDto persistedNutritionalInformationDto;

    @MockBean
    private NutritionalInformationRepository NutritionalInformationRepository;
    @MockBean
    private NutritionalInformationMapper NutritionalInformationMapper;
    @Bean
    NutritionalInformationService NutritionalInformationService() {
        return new NutritionalInformationService(NutritionalInformationRepository, NutritionalInformationMapper);
    }

    @BeforeEach
    void setUp() {
        NutritionalInformation NutritionalInformation = new NutritionalInformation("NutritionalInformationName");
        NutritionalInformationDto = new NutritionalInformationDto("NutritionalInformationName");
        persistedNutritionalInformation = new NutritionalInformation("NutritionalInformationName");
        persistedNutritionalInformationDto = new NutritionalInformationDto("NutritionalInformationName");
        Mockito.when(NutritionalInformationMapper.toEntity(any(NutritionalInformationDto.class))).thenReturn(NutritionalInformation);
    }

    @Test
    @DisplayName("Saving the NutritionalInformation entity")
    void save() {
        persistedNutritionalInformationDto.setId(1L);
        Mockito.when(NutritionalInformationRepository.save(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformation);
        Mockito.when(NutritionalInformationMapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformationDto);
        assertEquals(1,NutritionalInformationService().save(NutritionalInformationDto).getId());
    }

    @Test
    @DisplayName("Updating the NutritionalInformation entity")
    void update() {
        persistedNutritionalInformation.setNutrition("newNutritionalInformationName");
        NutritionalInformationDto persistedNutritionalInformationDto = new NutritionalInformationDto("newNutritionalInformationName");
        Mockito.when(NutritionalInformationRepository.save(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformation);
        Mockito.when(NutritionalInformationMapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformationDto);
        assertEquals("newNutritionalInformationName",NutritionalInformationService().update(NutritionalInformationDto).getNutrition());
    }

    @Test
    @DisplayName("Finding the NutritionalInformation entity by id")
    void findById() throws EntityNotFoundException {
        persistedNutritionalInformationDto.setId(1);
        Mockito.when(NutritionalInformationRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedNutritionalInformation));
        Mockito.when(NutritionalInformationMapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformationDto);
        NutritionalInformationDto resultDto = NutritionalInformationService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}