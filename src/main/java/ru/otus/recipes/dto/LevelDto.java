package ru.otus.recipes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LevelDto extends AbstractDto {
    public LevelDto(long id) {
        this.setId(id);
    }
}
