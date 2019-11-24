package ru.otus.recipes.service;

import lombok.Getter;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.CommonRepository;
import ru.otus.recipes.service.mapper.AbstractMapper;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class AbstractService< D extends AbstractDto,E extends AbstractEntity,
        R extends CommonRepository<E>, M extends AbstractMapper<D,E>>
        implements CommonService<D,E> {

    private final Logger log = LoggerFactory.getLogger(getClass().getTypeName());
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
    public D save(D dto) throws EntityExistsException {
        log.info(String.format("Start saving %s entity to database",dto.getClass().getTypeName()));
        if (repository.findById(dto.getId()).isPresent()) {
            throw new EntityExistsException(String.format("%s entity already exists!", entityClass.getTypeName()));
        }
        E entity = repository.save(mapper.toEntity(dto));
        log.info("Saving entity was successful");
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public List<D> saveAll(List<D> dtoList) {
        log.info("Start saving %s entities");
        List<E> entities = repository.saveAll(dtoList.stream().map(mapper::toEntity).collect(Collectors.toList()));
        log.info("Saving entities was successful");
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public D update(D dto) throws EntityNotFoundException {
        log.info(String.format("Start updating %s entity", dto.getClass().getTypeName()));
        if (!repository.findById(dto.getId()).isPresent()) {
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
        E entity = repository.save(mapper.toEntity(dto));
        log.info("Updating entity was successful");
        return mapper.toDto(entity);
    }

    @Override
    public D findById(Long id) throws EntityNotFoundException {
        log.info(String.format("Start getting %s entity with %d from database", entityClass.getTypeName(),id));
        E entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(
                String.format("No %s entity with id %d found!", entityClass.getTypeName(),id)));
        log.debug("{}",entity);
        log.info("Getting entity was successful");
        return mapper.toDto(entity);
    }

    @Override
    public List<D> findAll() throws EntityNotFoundException {
        log.info(String.format("Start getting %s entities from database", entityClass.getTypeName()));
        List<E> entities = repository.findAll();
        if (entities.isEmpty()) {
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
        log.info("Getting entities was successful");
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        log.info(String.format("Start removing %s entity with id %d from database", entityClass.getTypeName(),id));
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            log.error("Empty result returned",e);
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
        log.info("Removal entity was successful");
    }

    @Override
    public void deleteAll() throws EntityNotFoundException {
        log.info("Start removing all entities from database");
        try {
            repository.deleteAll();
        } catch (IllegalArgumentException e){
            log.error("Empty result returned",e);
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
        log.info("Removal entities was successful");
    }

    @Override
    public List<D> findAllByIds(List<Long> ids) throws EntityNotFoundException {
        log.info(String.format("Start getting %s entities with several ids from database", entityClass.getTypeName()));
        log.debug(String.format("Entities ids: %s", ids.stream().map(String::valueOf).collect(Collectors.joining())));
        List<E> entities =  repository.findByIdIn(ids);
        if (entities.isEmpty()) {
            throw new EntityNotFoundException(String.format("No %s entities found!", entityClass.getTypeName()));
        }
        log.info("Getting entities was successful");
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}