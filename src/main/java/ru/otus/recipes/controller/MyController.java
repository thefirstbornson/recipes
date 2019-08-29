package ru.otus.recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/test")
    public Object get() {
        throw new org.springframework.web.server.ResponseStatusException(
                HttpStatus.NO_CONTENT, "some message");
    }
}