package ru.otus.recipes.domain;


import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tblcuisine")
public class Cuisine extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cuisine_id")
    private Long id;
    @Column(name="cuisine")
    private String cuisine;
}
