package ru.otus.recipes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CuisineDto extends AbstractDto {
   private String cuisine;
}
