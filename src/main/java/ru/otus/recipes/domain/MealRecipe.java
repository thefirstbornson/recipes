package ru.otus.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mng_mealrecipe")
public class MealRecipe extends AbstractEntity {

    @Id
    @Column(name = "mealrecipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToMany
    @JoinTable(
            name = "mng_mealreciperecipes"
            ,joinColumns = @JoinColumn(name="mealrecipe_id")
            ,inverseJoinColumns = @JoinColumn(name = "recipe_id" )
    )
    private List<Recipe> recipes;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public MealRecipe(Long id,Meal meal, List<Recipe> recipes) {
        this.id = id;
        this.meal = meal;
        this.recipes = recipes;
    }

    public MealRecipe(Meal meal, List<Recipe> recipes, Menu menu) {
        this.meal = meal;
        this.recipes = recipes;
        this.menu = menu;
    }
}
