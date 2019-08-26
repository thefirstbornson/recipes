package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.Cuisine;
import ru.otus.recipes.dto.CuisineDto;
import ru.otus.recipes.repository.CuisineRepository;
import ru.otus.recipes.service.dtoconversion.CuisineMapper;

@Service
public class CuisineService extends AbstractService <CuisineDto,Cuisine, CuisineRepository, CuisineMapper>{
    @Autowired
    public CuisineService(CuisineRepository repository, CuisineMapper cuisineMapper) {
        super(repository, cuisineMapper);
    }
}
