package com.papaolabs.openapi.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.papaolabs.openapi.domain.service.BreedCategoryEnumSerializer;
import lombok.Data;

@Data
public class Breed {
    private Category category;
    private Integer code;
    private String name;

    @JsonSerialize(using = BreedCategoryEnumSerializer.class)
    public enum Category {
        DOG("417000"), CAT("422400"), ETC("429900");
        private final String code;

        Category(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
