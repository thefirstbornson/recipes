package ru.otus.recipes.service.mapper;

import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.dto.CuisineDto;

@Service
public class CuisineMapper extends AbstractMapper<CuisineDto, Cuisine> {
    CuisineMapper() {
        super(Cuisine.class, CuisineDto.class);
    }
}
