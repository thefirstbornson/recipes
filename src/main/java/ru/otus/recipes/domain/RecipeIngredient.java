package ru.otus.recipes.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblrecipeingredient")

public class RecipeIngredient  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_ingredient_id")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "recipe_id", nullable=false)
    private Recipe recipe;

    @ManyToOne()
    @JoinColumn(name = "ingredient_id", nullable=false)
    private Ingredient ingredient;

    @Column(name="amount")
    private int amount;

    @OneToOne
    @JoinColumn(name = "measurement_id")
    private Measurement measurement;

    public RecipeIngredient(Ingredient ingredient, int amount,Measurement measurement) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.measurement = measurement;
    }
}
