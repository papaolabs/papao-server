package com.papaolabs.image.infrastructure.feign.vision.dto;

import lombok.Data;

@Data
public class VisionTypeResponse {
    private String adult;
    private String spoof;
    private String medical;
    private String violence;
}
