package ru.otus.recipes.service;

import ru.otus.recipes.domain.AbstractEntity;

import java.util.List;
import java.util.Optional;


public interface CommonService<E extends AbstractEntity> {

    E save(E entity);

    List<E> saveAll(List<E> entities);

    E update(E entity);

    E findById(Long id);

    List<E> findAll();

    void deleteById(Long id);

    void deleteAll();
}
