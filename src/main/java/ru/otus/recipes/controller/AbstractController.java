package ru.otus.recipes.controller;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.service.CommonService;
import ru.otus.recipes.service.mapper.CompactJson;

import javax.persistence.Column;

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
    public ResponseEntity<?> save(@RequestBody D dto) throws EntityExistsException {
            log.info("Save request: {}",dto.toString());
            return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody D dto) throws EntityNotFoundException {
            log.info("Update request: {}",dto.toString());
            return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    @Override
    @CompactJson
    public ResponseEntity<?> get(@PathVariable long id) throws EntityNotFoundException {
            log.info("Get request with id: {}",id);
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() throws EntityNotFoundException {
            log.info("Get request all entities");
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable long id) throws EntityNotFoundException {
        log.info("Delete request with id: {}", id);
        service.deleteById(id);
        return new ResponseEntity<>("Removal was successful", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> deleteAll() throws EntityNotFoundException {
            log.info("Delete request entities");
            service.deleteAll();
            return new ResponseEntity<>("Removal was successful",HttpStatus.OK);
    }
}
