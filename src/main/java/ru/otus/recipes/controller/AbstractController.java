package ru.otus.recipes.controller;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.service.CommonService;

@Getter
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>, D extends AbstractDto>
        implements CommonController<D> {

    private final S service;
    private Class<E> entityClass;

    @Autowired
    private ModelMapper modelMapper;

    protected AbstractController(S service, Class<E> entityClass) {
        this.service = service;
        this.entityClass = entityClass;
    }

    @Override
    public ResponseEntity<?> save(@RequestBody D dto) {
        service.save(modelMapper.map(dto, entityClass));
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }

//    @Override
//    public ResponseEntity<?> saveAll(@RequestBody List<E> entities) {
//        return ResponseEntity.ok(service.saveAll(entities));
//    }

    @Override
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody D dto) {
        this.save(dto);
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.findById(id),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
       return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Override
    public  ResponseEntity<?> delete(@PathVariable("id") long id) {
        service.deleteById(id);
        return new ResponseEntity<>(
                "{\"status\":\"deleted\"}",
                HttpStatus.OK
        );
    }

    @Override
    public  ResponseEntity<?> deleteAll() {
        service.deleteAll();
        return new ResponseEntity<>(
                "{\"status\":\"deleted\"}",
                HttpStatus.OK
        );    }
}
