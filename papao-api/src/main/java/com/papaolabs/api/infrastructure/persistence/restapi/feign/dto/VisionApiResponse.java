package com.papaolabs.api.infrastructure.persistence.restapi.feign.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Data
public class VisionApiResponse {
    private List<VisionResult> responses;

    @Data
    public static class VisionResult {
        private List<Label> labelAnnotations = Arrays.asList();
        private Type safeSearchAnnotation = new Type();
        private VisionProperties imagePropertiesAnnotation = new VisionProperties();

        @Data
        public static class Label {
            private String mid = EMPTY;
            private String description = EMPTY;
            private Double score = -1.0;
        }

        @Data
        public static class VisionProperties {
            private DominantColor dominantColors = new DominantColor();

            @Data
            public static class DominantColor {
                List<Properties> colors = Arrays.asList();

                @Data
                public static class Properties {
                    private Color color = new Color();
                    private Double score = -1.0;
                    private Double pixelFraction = -1.0;

                    @Data
                    public static class Color {
                        private Integer red = -1;
                        private Integer green = -1;
                        private Integer blue = -1;
                    }
                }
            }
        }

        @Data
        public static class Type {
            private String adult = EMPTY;
            private String spoof = EMPTY;
            private String medical = EMPTY;
            private String violence = EMPTY;
        }
    }
}
