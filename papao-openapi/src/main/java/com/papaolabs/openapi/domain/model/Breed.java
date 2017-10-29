package com.papaolabs.openapi.domain.model;

import lombok.Data;

@Data
public class Breed {
    private String category;
    private Integer code;
    private String name;

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
