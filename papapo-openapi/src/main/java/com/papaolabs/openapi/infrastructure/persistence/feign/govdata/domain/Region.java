package com.papaolabs.openapi.infrastructure.persistence.feign.govdata.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Region {
    private Integer sidoCode;
    private Integer gunguCode;
    private String sidoName;
    private String gunguName;
}
