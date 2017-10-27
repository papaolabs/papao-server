package com.papaolabs.client.vision.dto;

import lombok.Data;

@Data
public class VisionColorResponse {
    private RgbColor rgbColor;
    private Double score;
    private Double pixelFraction;

    @Data
    public static class RgbColor {
        private Integer red;
        private Integer green;
        private Integer blue;
    }
}
