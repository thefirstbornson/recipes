package ru.otus.recipes.service.mapper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class CompressionCriteria {
    @Singular("expansionsList") private List<String> expansionsList;
    @Singular("includingsList") private List<String> includingsList;
    @Builder.Default private StringBuilder path = new StringBuilder();
    private String delimiter;
    private String identifier;

    public StringBuilder appendPath(String pathToAppend){
       return this.path.append(pathToAppend);
    }
}
