package ru.otus.recipes.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblrecipeingredient")

public class RecipeIngredient  extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_ingredient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable=false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, int amount, Measurement measurement) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.measurement = measurement;
    }
}
