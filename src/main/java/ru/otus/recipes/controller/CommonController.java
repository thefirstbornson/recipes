package ru.otus.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.exception.EntityNotFoundException;


public interface CommonController<D extends AbstractDto> {

    @PostMapping
    ResponseEntity<?> save(@RequestBody D dto);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody D dto);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable("id") long id);

    @GetMapping
    ResponseEntity<?> getAll() throws EntityNotFoundException;

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") long id);

    @DeleteMapping
    ResponseEntity<?> deleteAll();
}
