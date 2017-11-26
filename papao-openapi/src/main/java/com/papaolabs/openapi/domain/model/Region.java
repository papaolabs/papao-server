package com.papaolabs.openapi.domain.model;

import lombok.Data;

@Data
public class Region {
    private Long id;
    private Integer sidoCode;
    private String sidoName;
    private Integer gunguCode;
    private String gunguName;
}
