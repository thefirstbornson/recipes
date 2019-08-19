package ru.otus.recipes.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CourseDto extends AbstractDto {
   private String course;
}
