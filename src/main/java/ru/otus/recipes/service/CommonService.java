package ru.otus.recipes.service;

import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.exception.EntityMapperException;
import ru.otus.recipes.exception.EntityNotFoundException;

import java.util.List;


public interface CommonService< D extends AbstractDto,E extends AbstractEntity> {

    D save(D dto) throws EntityMapperException;

    List<D> saveAll(List<D> dtoList);

    D update(D dto) throws EntityMapperException;

    D findById(Long id) throws EntityNotFoundException;

    List<D> findAllByIds(List<Long> Ids) throws EntityNotFoundException;

    List<D> findAll() throws EntityNotFoundException;

    void deleteById(Long id) throws EntityNotFoundException;

    void deleteAll() throws EntityNotFoundException;
}
