package ru.otus.recipes.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ingredient_id")
    long id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private Set<IngredientNutritionalInformation> ingredientNutritionalInformations;

    public Ingredient(String name, List<IngredientNutritionalInformation> ingredientNutritionalInformationList) {
        this.name = name;
        for(IngredientNutritionalInformation ingredientNutritionalInformation : ingredientNutritionalInformationList) {
            ingredientNutritionalInformation.setIngredient(this);
        }
        this.ingredientNutritionalInformations = new HashSet<>(ingredientNutritionalInformationList);
    }

}
