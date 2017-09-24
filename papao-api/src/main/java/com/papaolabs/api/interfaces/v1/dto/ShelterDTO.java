package com.papaolabs.api.interfaces.v1.dto;

import lombok.Data;

@Data
public class ShelterDTO {
    private String cityCode;
    private String cityName;
    private String townCode;
    private String townName;
    private String shelterCode;
    private String shelterName;
}
