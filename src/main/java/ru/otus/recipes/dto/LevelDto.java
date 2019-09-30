package ru.otus.recipes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LevelDto extends AbstractDto {
    public LevelDto(long id) {
        this.setId(id);
    }
}
