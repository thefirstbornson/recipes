package ru.otus.recipes.service.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompactJson {
    List<String> expansions = Collections.emptyList();
    List<String> includings = Collections.emptyList();
}
