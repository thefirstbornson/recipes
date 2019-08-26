package ru.otus.recipes.service;

import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;

import java.util.List;
import java.util.Optional;


public interface CommonService< D extends AbstractDto,E extends AbstractEntity> {

    D save(D dto);

    List<D> saveAll(List<D> dtoList);

    D update(D dto);

    D findById(Long id);

    List<E> findAll();

    void deleteById(Long id);

    void deleteAll();
}
