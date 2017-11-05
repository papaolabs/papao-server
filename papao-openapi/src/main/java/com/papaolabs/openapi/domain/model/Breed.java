package com.papaolabs.openapi.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.papaolabs.openapi.domain.service.BreedSpeciesEnumSerializer;
import lombok.Data;

@Data
public class Breed {
    private Long id;
    private Species species;
    private Integer code;
    private String name;

    @JsonSerialize(using = BreedSpeciesEnumSerializer.class)
    public enum Species {
        DOG("417000"), CAT("422400"), ETC("429900");
        private final String code;

        Species(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
