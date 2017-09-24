package com.papaolabs.api.interfaces.v1.dto;

import lombok.Data;

@Data
public class ShelterDTO {
    private Long cityCode;
    private String cityName;
    private Long townCode;
    private String townName;
    private Long shelterCode;
    private String shelterName;
}
