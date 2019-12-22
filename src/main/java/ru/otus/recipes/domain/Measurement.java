package ru.otus.recipes.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblmeasurement")
public class Measurement extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="measurement_id")
    Long id;
    @Column(name="name")
    private String name;
}
