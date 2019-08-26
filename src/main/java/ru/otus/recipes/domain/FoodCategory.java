package ru.otus.recipes.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblfoodcategory")
public class FoodCategory extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_category_id")
    private long id;
    @Column(name="food_category")
    private String foodCategory;
}
