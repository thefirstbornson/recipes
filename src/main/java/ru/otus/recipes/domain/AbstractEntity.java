package ru.otus.recipes.domain;

import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@ToString
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    public abstract Long getId();
}

