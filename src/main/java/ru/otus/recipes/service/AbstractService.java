package ru.otus.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.repository.CommonRepository;
import ru.otus.recipes.service.dtoconversion.AbstractMapper;
import ru.otus.recipes.service.dtoconversion.Mapper;

import java.util.List;
import java.util.stream.Collectors;


public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>>
        implements CommonService<E> {

    private final R repository;
    private final M mapper;
    private Class<E> entityClass;

    @Autowired
    public AbstractService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public D save(D dto) {
        E entity = repository.save(mapper.toEntity(dto));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public List<D> saveAll(List<D> dtoList) {
        List<E> entities = repository.saveAll(dtoList.stream().map(mapper::toEntity).collect(Collectors.toList()));
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public D update(D dto) {
        return save(dto);
    }

    @Override
    public D findById(Long id) {
        E entity = repository.findById(id).orElseThrow(()->new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityClass.getTypeName(), id.intValue()), 1));
        return mapper.toDto(entity);
    }

    @Override
    public List<D> findAll() {
        List<E> entities = repository.findAll();
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
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