package ru.otus.recipes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblnutritionalinformation")
public class NutritionalInformation extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="nutrition_information_id")
    long id;
    @Column(name="name")
    private String nutrition;
    @OneToMany(mappedBy = "nutrition", cascade = CascadeType.ALL)
    private Set<IngredientNutritionalInformation> ingredientNutritionalInformationSet;

    public NutritionalInformation(String nutrition) {
        this.nutrition = nutrition;
    }
}
