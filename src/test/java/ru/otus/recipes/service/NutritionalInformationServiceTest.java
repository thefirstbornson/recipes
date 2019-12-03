package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.NutritionalInformation;
import ru.otus.recipes.dto.NutritionalInformationDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.NutritionalInformationRepository;
import ru.otus.recipes.service.mapper.NutritionalInformationMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class NutritionalInformationServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private NutritionalInformation persistedEntity;
    private NutritionalInformationDto dto;
    private NutritionalInformationDto persistedDto;

    @MockBean
    private NutritionalInformationRepository repository;
    @MockBean
    private NutritionalInformationMapper mapper;
    private NutritionalInformationService service;

    @BeforeEach
    void setUp() {
        service =
                new NutritionalInformationService(repository, mapper);
        NutritionalInformation NutritionalInformation = new NutritionalInformation("NutritionalInformationName");
        dto = new NutritionalInformationDto("NutritionalInformationName");
        persistedEntity = new NutritionalInformation("NutritionalInformationName");
        persistedDto = new NutritionalInformationDto("NutritionalInformationName");
        Mockito.when(mapper.toEntity(any(NutritionalInformationDto.class))).thenReturn(NutritionalInformation);
    }

    @Test
    @DisplayName("Сохранение  nutritionalInformation entity")
    void save() throws EntityExistsException {
        persistedDto.setId(1L);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(NutritionalInformation.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedDto);
        assertEquals(1, service.save(dto).getId());
    }

    @Test
    @DisplayName("Обновление nutritionalInformation entity")
    void update() throws EntityNotFoundException {
        persistedEntity.setNutrition("newNutritionalInformationName");
        NutritionalInformationDto persistedNutritionalInformationDto = new NutritionalInformationDto("newNutritionalInformationName");
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        Mockito.when(repository.save(any(NutritionalInformation.class))).thenReturn(persistedEntity);
        Mockito.when(mapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedNutritionalInformationDto);
        assertEquals("newNutritionalInformationName", service.update(dto).getNutrition());
    }

    @Test
    @DisplayName("Поиск nutritionalInformation entity по id")
    void findById() throws EntityNotFoundException {
        persistedDto.setId(1);
        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedEntity));
        Mockito.when(mapper.toDto(any(NutritionalInformation.class))).thenReturn(persistedDto);
        NutritionalInformationDto resultDto = service.findById(1L);
        assertEquals(1L,resultDto.getId());
    }

    @Test
    @DisplayName("Ошибка при сохранении существующей entity")
    void saveEntityExistsException() {
        dto.setId(DTO_ID);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(persistedEntity));
        assertThrows(EntityExistsException.class, () -> service.save(dto));
    }

    @Test
    @DisplayName("Ошибка при обновлении несуществующей entity")
    void updateEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("Ошибка при поиске несуществующей entity")
    void findByIdEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(DTO_ID));
    }

    @Test
    @DisplayName("Ошибка при удалении несуществующей entity")
    void deleteByIdEntityNotFoundException() {
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteById(DTO_ID));
    }
}