package ru.otus.recipes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
    private long id;
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
            ,joinColumns = @JoinColumn(name="recipe_id")
            ,inverseJoinColumns = @JoinColumn(name = "meal_id" )
    )
    private Set<Recipe> recipes;

//   /**
//    *
//    * Базовые названия приёмов пищи необходимые для инициализации стратегии.
//    * Обязательно должны присутствовать при инициализации данных в БД в таблице tbl_meals
//    */
//    public enum Type {
//        BREAKFAST(1),
//        LUNCH(2),
//        DINNER(3),
//        NOSH(4);
//
//       private final long value;
//
//       Type(long value) {
//            this.value = value;
//        }
//
//       public long getValue() {
//           return value;
//       }
//   }
}
