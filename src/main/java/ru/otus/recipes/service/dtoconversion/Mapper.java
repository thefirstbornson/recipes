package ru.otus.recipes.service.dtoconversion;

import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.domain.Recipe;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.dto.RecipeDto;

public interface Mapper<D extends AbstractDto,E extends AbstractEntity> {
    D toDto(E entity);
    E toEntity(D dto);
}
