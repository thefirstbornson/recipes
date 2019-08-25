package ru.otus.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;

import java.util.List;


public interface CommonController<D extends AbstractDto> {

    @PostMapping
    ResponseEntity<?> save(@RequestBody D dto);

//    @PostMapping
//    ResponseEntity<?> saveAll(@RequestBody List<E> entities);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody D dto);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") long id);

    @GetMapping
    ResponseEntity<?> getAll();

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") long id);

    @DeleteMapping
    ResponseEntity<?> deleteAll();
}
