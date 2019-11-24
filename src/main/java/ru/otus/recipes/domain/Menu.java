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
@Table(name = "mng_menu")
public class Menu extends AbstractEntity {
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy ="menu")
    private Set<MealRecipe> mealRecipes ;

//    @OneToOne(mappedBy = "menu", optional = false, fetch = FetchType.LAZY)
//    private DailyMenu dailyMenu;
}
