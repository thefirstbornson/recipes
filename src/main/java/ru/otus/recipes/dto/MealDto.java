package ru.otus.recipes.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MealDto extends AbstractDto {
    private String meal;
}
