package ru.otus.recipes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mng_menu")
public class Menu extends AbstractEntity {
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy ="menu",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MealRecipe> mealRecipes ;

//    @OneToOne(mappedBy = "menu", optional = false, fetch = FetchType.LAZY)
//    private DailyMenu dailyMenu;
}
