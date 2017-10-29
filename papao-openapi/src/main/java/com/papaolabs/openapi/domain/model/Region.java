package com.papaolabs.openapi.domain.model;

import lombok.Data;

@Data
public class Region {
    private Integer cityCode;
    private String cityName;
    private Integer townCode;
    private String townName;
}
