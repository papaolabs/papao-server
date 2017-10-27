package com.papaolabs.api.infrastructure.persistence.restapi.vision.dto;

import lombok.Data;

@Data
public class VisionColorResponse {
    private String RgbCode;
    private Double score;
    private Double pixelFraction;
    
    @Data
    public static class RgbColor {
        private Integer red;
        private Integer green;
        private Integer blue;
    }
}
