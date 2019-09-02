package ru.otus.recipes.controller;

import lombok.Getter;
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

    private final S service;
    private Class<E> entityClass;

    protected AbstractController(S service, Class<E> entityClass) {
        this.service = service;
        this.entityClass = entityClass;
    }

    @Override
    public ResponseEntity<?> save(@RequestBody D dto) {
        try {
            return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
        } catch (EntityMapperException ex) {
            throw new ResponseStatusException( HttpStatus.NOT_IMPLEMENTED, "Can not save entity", ex);
        }
    }

    @Override
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody D dto) {
        try {
            return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
        } catch (EntityMapperException ex) {
            throw new ResponseStatusException( HttpStatus.NOT_IMPLEMENTED, "Can not update entity", ex);
        }
    }

    @Override
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.OK, String.format("Can not find entity with id  %d", id), ex);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException( HttpStatus.OK, "Can not find entities", ex);
        }
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException( HttpStatus.OK, String.format("Can not delete entity with %d",id), ex);
        }
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        try {
            service.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException( HttpStatus.OK, "Can not delete entities", ex);
        }
    }
}
