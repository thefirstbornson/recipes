package ru.otus.recipes.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblingredientnutritionaninformation")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class IngredientNutritionalInformation extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rni_id")
    private long id;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne()
    @JoinColumn(name = "ni_id")
    private NutritionalInformation nutrition;

    @Column(name="amount")
    private int amount;

    public IngredientNutritionalInformation(NutritionalInformation nutrition, int amount) {
        this.nutrition = nutrition;
        this.amount = amount;
    }
}
