package ru.otus.recipes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tblmeal")

public class Meal extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="meal_id")
    private Long id;
    @Column(name="meal")
    private String meal;

    public Meal(Long id,String meal) {
        this.id =id;
        this.meal = meal;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "tblrecipemeals"
            ,joinColumns = @JoinColumn(name="meal_id")
            ,inverseJoinColumns = @JoinColumn(name = "recipe_id" )
    )
    private Set<Recipe> recipes = new HashSet<>();
}
