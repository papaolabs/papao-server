package com.papaolabs.openapi.domain.model;

import lombok.Data;

@Data
public class Shelter {
    private Long id;
    private Region region;
    private String code;
    private String name;
}
