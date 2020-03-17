package ru.otus.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.recipes.dto.AbstractDto;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;


public interface CommonController<D extends AbstractDto> {

    @PostMapping
    ResponseEntity<?> save(@RequestBody D dto) throws EntityExistsException;

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody D dto) throws EntityNotFoundException;

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable long id, @RequestParam(value = "expand") String[] expansions) throws EntityNotFoundException;

    @GetMapping
    ResponseEntity<?> getAll() throws EntityNotFoundException;

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") long id) throws EntityNotFoundException;

    @DeleteMapping
    ResponseEntity<?> deleteAll() throws EntityNotFoundException;
}
