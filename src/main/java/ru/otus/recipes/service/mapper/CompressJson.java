package ru.otus.recipes.service.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompressJson {
    String expansions() default "";
    String includings() default "";
    String delimiter() default ".";
    String identifier() default "id";
}
