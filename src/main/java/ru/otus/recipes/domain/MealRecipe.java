package ru.otus.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mng_mealrecipe")
public class MealRecipe {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mealRecipeSetId;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mngmealreciperecipes"
            ,joinColumns = @JoinColumn(name="mealrecipe_id")
            ,inverseJoinColumns = @JoinColumn(name = "recipe_id" )
    )
    private Set<Recipe> recipes;
}
