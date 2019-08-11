package ru.otus.recipes.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="tblcuisine")
public class Cuisine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cuisine_id")
    private long id;
    @Column(name="cuisine")
    private String cuisine;

}
