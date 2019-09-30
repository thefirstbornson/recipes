package ru.otus.recipes.domain;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblrecipe")
public class Recipe  extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="instructions")
    private String instructions;

    @Column(name="cooktime")
    private  int cooktime;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;

    @Column(name="rating")
    private int rating;

    @ManyToMany
    @JoinTable(
            name = "tblrecipecourse"
            ,joinColumns = @JoinColumn(name="recipe_id")
            ,inverseJoinColumns = @JoinColumn(name = "course_id" )
    )
    private Set<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "tblrecipefoodcategory"
            ,joinColumns = @JoinColumn(name="recipe_id")
            ,inverseJoinColumns = @JoinColumn(name = "food_category_id" )
    )
    private Set<FoodCategory> foodCategories= new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tblrecipemeals"
            ,joinColumns = @JoinColumn(name="recipe_id")
            ,inverseJoinColumns = @JoinColumn(name = "meal_id" )
    )
    private Set<Meal> meals;
    @Column(name="imagepath")
    private String imagepath;
    @OneToMany(mappedBy ="recipe",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<RecipeIngredient> recipeIngredients ;

    public Recipe(long id, String name, String description, String instructions, int cooktime,
                  int rating, String imagepath, Level level, Cuisine cuisine, Set<Course> courses,
                  Set<FoodCategory> foodCategories, Set<Meal> meals, List<RecipeIngredient> recipeIngredients ) {
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.cooktime = cooktime;
        this.rating = rating;
        this.imagepath = imagepath;
        this.level=level;
        this.cuisine = cuisine;
        this.courses=courses;
        this.foodCategories=foodCategories;
        this.meals=meals;
        for(RecipeIngredient  recipeIngredient : recipeIngredients) {
            recipeIngredient.setRecipe(this);
        }
        this.recipeIngredients = new HashSet<>(recipeIngredients);
    }
}