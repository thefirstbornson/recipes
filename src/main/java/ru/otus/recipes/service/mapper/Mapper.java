package ru.otus.recipes.service.mapper;

import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;

public interface Mapper<D extends AbstractDto,E extends AbstractEntity> {
    D toDto(E entity);
    E toEntity(D dto);
}
