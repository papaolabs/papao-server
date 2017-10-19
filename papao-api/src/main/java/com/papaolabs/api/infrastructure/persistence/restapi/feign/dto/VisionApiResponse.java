package com.papaolabs.api.infrastructure.persistence.restapi.feign.dto;

import lombok.Data;

import java.util.List;

@Data
public class VisionApiResponse {
    private List<VisionResult> responses;

    @Data
    public static class VisionResult {
        private List<Label> labelAnnotations;
        private Type safeSearchAnnotation;
        private VisionProperties imagePropertiesAnnotation;

        @Data
        public static class Label {
            private String mid;
            private String description;
            private Double score;
        }

        @Data
        public static class VisionProperties {
            private DominantColor dominantColors;

            @Data
            public static class DominantColor {
                List<Properties> colors;

                @Data
                public static class Properties {
                    private Color color;
                    private Double score;
                    private Double pixelFraction;

                    @Data
                    public static class Color {
                        private Integer red;
                        private Integer green;
                        private Integer blue;
                    }
                }
            }
        }

        @Data
        public static class Type {
            private String adult;
            private String spoof;
            private String medical;
            private String violence;
        }
    }
}
