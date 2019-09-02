package ru.otus.recipes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.springframework.lang.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Getter
public class MealDto extends AbstractDto {
    private String meal;
}
