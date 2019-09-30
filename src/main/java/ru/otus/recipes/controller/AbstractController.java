package ru.otus.recipes.controller;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.exception.EntityMapperException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.service.CommonService;

@Getter
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<D,E>, D extends AbstractDto>
        implements CommonController<D> {

    private final Logger log = LoggerFactory.getLogger(getClass().getTypeName());
    private final S service;
    private Class<E> entityClass;

    protected AbstractController(S service, Class<E> entityClass) {
        this.service = service;
        this.entityClass = entityClass;
    }

    @Override
    public ResponseEntity<?> save(@RequestBody D dto) {
        try {
            log.info("Save request: {}",dto.toString());
            return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
        } catch (EntityMapperException ex) {
            log.error("Save request failed", ex);
            throw new ResponseStatusException( HttpStatus.NOT_IMPLEMENTED, "Can not save entity", ex);
        }
    }

    @Override
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody D dto) {
        try {
            log.info("Update request: {}",dto.toString());
            return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
        } catch (EntityMapperException ex) {
            log.error("Update request failed", ex);
            throw new ResponseStatusException( HttpStatus.NOT_IMPLEMENTED, "Can not update entity", ex);
        }
    }

    @Override
    public ResponseEntity<?> get(@PathVariable long id) {
        try {
            log.info("Get request with id: {}",id);
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            log.error("Get request failed", ex);
            throw new ResponseStatusException(HttpStatus.OK, String.format("Can not find entity with id  %d", id), ex);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            log.info("Get request all entities");
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            log.error("Get request failed", ex);
            throw new ResponseStatusException( HttpStatus.OK, "Can not find entities", ex);
        }
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            log.info("Delete request with id: {}",id);
            service.deleteById(id);
            return new ResponseEntity<>("Removal was successful",HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            log.error("Delete request failed", ex);
            throw new ResponseStatusException( HttpStatus.OK, String.format("Can not delete entity with %d",id), ex);
        }
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        try {
            log.info("Delete request entities");
            service.deleteAll();
            return new ResponseEntity<>("Removal was successful",HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            log.error("Delete request failed", ex);
            throw new ResponseStatusException( HttpStatus.OK, "Can not delete entities", ex);
        }
    }
}
