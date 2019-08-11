package ru.otus.recipes.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.otus.recipes.dto.RecipeDto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class RecipeSerialization extends StdSerializer<RecipeDto> {

    public RecipeSerialization(Class t) {
        super(t);
    }

    @Override
    public void serialize(RecipeDto recipe, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//
//        jsonGenerator.writeStartObject();
//        jsonGenerator.writeStringField("imdbId", actor.getImdbId());
//        jsonGenerator.writeObjectField("dateOfBirth", actor.getDateOfBirth() != null ? sdf.format(actor.getDateOfBirth()) : null);
//        jsonGenerator.writeNumberField("N° Film: ", actor.getFilmography() != null ? actor.getFilmography()
//                .size() : null);
//        jsonGenerator.writeStringField("filmography", actor.getFilmography()
//                .stream()
//                .collect(Collectors.joining("-")));
//        jsonGenerator.writeEndObject();
    }
}
