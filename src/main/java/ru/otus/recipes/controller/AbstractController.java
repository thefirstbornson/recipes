package ru.otus.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.service.CommonService;

import javax.lang.model.type.ErrorType;
import java.util.List;

public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>>
        implements CommonController<E> {

    private final S service;

    @Autowired
    protected AbstractController(S service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> save(@RequestBody E entity) {
       service.save(entity);
       return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }

//    @Override
//    public ResponseEntity<?> saveAll(@RequestBody List<E> entities) {
//        return ResponseEntity.ok(service.saveAll(entities));
//    }

    @Override
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody E entity) {
        service.save(entity);
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.findById(id),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<E>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public void delete(@PathVariable("id") long id) {
       service.deleteById(id);
    }

    @Override
    public void deleteAll() {
        service.deleteAll();
    }
}
