package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.repository.CommonRepository;

import java.util.List;


public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>>
        implements CommonService<E> {

    private final R repository;
    private Class<E> entityClass;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public List<E> saveAll(List<E> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public E update(E entity) {
        return repository.save(entity);
    }

    @Override
    public E findById(Long id) {
        return repository.findById(id).orElseThrow(()->new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityClass.getTypeName(), id.intValue()), 1));
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}