package ru.otus.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mng_dailymenu")
public class DailyMenu {

    @Id
    @Column(name = "dailymenu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
