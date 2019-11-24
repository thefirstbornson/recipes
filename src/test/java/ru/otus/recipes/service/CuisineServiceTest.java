package ru.otus.recipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.dto.CuisineDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.CuisineRepository;
import ru.otus.recipes.service.mapper.CuisineMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class CuisineServiceTest {
    private static final Long ID =1L;
    private static final Long DTO_ID =1L;
    private Cuisine persistedCuisine;
    private CuisineDto cuisineDto;
    private CuisineDto persistedCuisineDto;

    @MockBean
    private CuisineRepository cuisineRepository;
    @MockBean
    private CuisineMapper cuisineMapper;
    private CuisineService cuisineService;

    @BeforeEach
    void setUp() {
        cuisineService =new CuisineService(cuisineRepository, cuisineMapper);
        Cuisine cuisine = new Cuisine(0, "CuisineName");
        cuisineDto = new CuisineDto("CuisineName");
        persistedCuisine = new Cuisine(ID,"CuisineName");
        persistedCuisineDto = new CuisineDto("CuisineName");
        Mockito.when(cuisineMapper.toEntity(any(CuisineDto.class))).thenReturn(cuisine);
    }

    @Test
    @DisplayName("Saving the Cuisine entity")
    void save() throws EntityExistsException {
        persistedCuisineDto.setId(DTO_ID);
        Mockito.when(cuisineRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(cuisineRepository.save(any(Cuisine.class))).thenReturn(persistedCuisine);
        Mockito.when(cuisineMapper.toDto(any(Cuisine.class))).thenReturn(persistedCuisineDto);
        assertEquals(DTO_ID,cuisineService.save(cuisineDto).getId());
    }

    @Test
    @DisplayName("Updating the Cuisine entity")
    void update() throws EntityNotFoundException {
        Cuisine persistedCuisine = new Cuisine(1,"CuisineName");
        persistedCuisine.setCuisine("newCuisineName");
        persistedCuisineDto.setCuisine("newCuisineName");
        Mockito.when(cuisineRepository.findById(anyLong())).thenReturn(Optional.of(persistedCuisine));
        Mockito.when(cuisineRepository.save(any(Cuisine.class))).thenReturn(persistedCuisine);
        Mockito.when(cuisineMapper.toDto(any(Cuisine.class))).thenReturn(persistedCuisineDto);
        assertEquals("newCuisineName",cuisineService.update(cuisineDto).getCuisine());
    }

    @Test
    @DisplayName("Finding the Cuisine entity by id")
    void findById() throws EntityNotFoundException {
        persistedCuisineDto.setId(DTO_ID);
        Mockito.when(cuisineRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedCuisine));
        Mockito.when(cuisineMapper.toDto(any(Cuisine.class))).thenReturn(persistedCuisineDto);
        CuisineDto resultDto = cuisineService.findById(1L);
        assertEquals(DTO_ID,resultDto.getId());
    }
}