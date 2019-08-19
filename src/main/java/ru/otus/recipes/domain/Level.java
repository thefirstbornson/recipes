package ru.otus.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbllevel")
public class Level extends AbstractEntity {
    @Id
    @Column(name="level_id")
    private long id;
}
