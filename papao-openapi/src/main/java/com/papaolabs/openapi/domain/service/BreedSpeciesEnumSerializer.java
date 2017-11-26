package com.papaolabs.openapi.domain.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.papaolabs.openapi.domain.model.Breed;

import java.io.IOException;

public class BreedSpeciesEnumSerializer extends JsonSerializer<Breed.Species> {
    @Override
    public void serialize(Breed.Species value, JsonGenerator generator,
                          SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeString(value.name());
        generator.writeFieldName("code");
        generator.writeString(value.getCode());
        generator.writeEndObject();
    }
}
