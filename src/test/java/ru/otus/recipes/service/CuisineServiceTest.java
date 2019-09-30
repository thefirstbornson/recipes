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
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.CuisineRepository;
import ru.otus.recipes.service.mapper.CuisineMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class CuisineServiceTest {

    private Cuisine persistedCuisine;
    private CuisineDto cuisineDto;
    private CuisineDto persistedCuisineDto;

    @MockBean
    private CuisineRepository cuisineRepository;
    @MockBean
    private CuisineMapper cuisineMapper;

    @Bean
    CuisineService cuisineService() {
        return new CuisineService(cuisineRepository, cuisineMapper);
    }

    @BeforeEach
    void setUp() {
        Cuisine cuisine = new Cuisine(0, "CuisineName");
        cuisineDto = new CuisineDto("CuisineName");
        persistedCuisine = new Cuisine(1,"CuisineName");
        persistedCuisineDto = new CuisineDto("CuisineName");
        Mockito.when(cuisineMapper.toEntity(any(CuisineDto.class))).thenReturn(cuisine);
    }

    @Test
    @DisplayName("Saving the Cuisine entity")
    void save() {
        persistedCuisineDto.setId(1L);
        Mockito.when(cuisineRepository.save(any(Cuisine.class))).thenReturn(persistedCuisine);
        Mockito.when(cuisineMapper.toDto(any(Cuisine.class))).thenReturn(persistedCuisineDto);
        assertEquals(1,cuisineService().save(cuisineDto).getId());
    }

    @Test
    @DisplayName("Updating the Cuisine entity")
    void update() {
        Cuisine persistedCuisine = new Cuisine(1,"CuisineName");
        persistedCuisine.setCuisine("newCuisineName");
        persistedCuisineDto.setCuisine("newCuisineName");
        Mockito.when(cuisineRepository.save(any(Cuisine.class))).thenReturn(persistedCuisine);
        Mockito.when(cuisineMapper.toDto(any(Cuisine.class))).thenReturn(persistedCuisineDto);
        assertEquals("newCuisineName",cuisineService().update(cuisineDto).getCuisine());
    }

    @Test
    @DisplayName("Finding the Cuisine entity by id")
    void findById() throws EntityNotFoundException {
        persistedCuisineDto.setId(1);
        Mockito.when(cuisineRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persistedCuisine));
        Mockito.when(cuisineMapper.toDto(any(Cuisine.class))).thenReturn(persistedCuisineDto);
        CuisineDto resultDto = cuisineService().findById(1L);
        assertEquals(1L,resultDto.getId());
    }
}