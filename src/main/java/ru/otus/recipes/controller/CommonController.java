package ru.otus.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.domain.AbstractEntity;

import java.util.List;


public interface CommonController<E extends AbstractEntity> {

    @PostMapping
    ResponseEntity<?> save(@RequestBody E entity);

//    @PostMapping
//    ResponseEntity<?> saveAll(@RequestBody List<E> entities);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody E entity);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") long id);

    @GetMapping
    ResponseEntity<List<E>> getAll();

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") long id);

    @DeleteMapping
    void deleteAll();
}
