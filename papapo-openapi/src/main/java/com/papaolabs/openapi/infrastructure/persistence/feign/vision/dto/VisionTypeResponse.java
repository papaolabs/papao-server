package com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto;

import lombok.Data;

@Data
public class VisionTypeResponse {
    private String adult;
    private String spoof;
    private String medical;
    private String violence;
}
