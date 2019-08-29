package ru.otus.recipes.service;

import lombok.Getter;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.exception.EntityMapperException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.CommonRepository;
import ru.otus.recipes.service.mapper.AbstractMapper;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class AbstractService< D extends AbstractDto,E extends AbstractEntity,
        R extends CommonRepository<E>, M extends AbstractMapper<D,E>>
        implements CommonService<D,E> {

    private final R repository;
    private final M mapper;
    private Class<E> entityClass;

    @Autowired
    public AbstractService(R repository, M mapper, Class<E> entityClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public D save(D dto) throws EntityMapperException {
        try {
            E entity = repository.save(mapper.toEntity(dto));
            return mapper.toDto(entity);
        } catch (MappingException ex){
            ex.printStackTrace();
            throw new EntityMapperException(String.format("Can not map Dto to %s entity!", entityClass.getTypeName() ), ex);
        }
    }

    @Override
    @Transactional
    public List<D> saveAll(List<D> dtoList) {
        List<E> entities = repository.saveAll(dtoList.stream().map(mapper::toEntity).collect(Collectors.toList()));
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public D update(D dto) throws EntityMapperException {
        return save(dto);
    }

    @Override
    public D findById(Long id) throws EntityNotFoundException {
        E entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(
                String.format("No %s entity with id %s found!", entityClass.getTypeName(),id)));
        return mapper.toDto(entity);
    }

    @Override
    public List<D> findAll() throws EntityNotFoundException {
        List<E> entities = repository.findAll();
        if (entities.isEmpty()) {
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
    }

    @Override
    public void deleteAll() throws EntityNotFoundException {
        try {
           repository.deleteAll();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }

    }

    @Override
    public List<D> findAllByIds(List<Long> ids) throws EntityNotFoundException {
        List<E> entities =  repository.findByIdIn(ids);
        if (entities.isEmpty()) {
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}