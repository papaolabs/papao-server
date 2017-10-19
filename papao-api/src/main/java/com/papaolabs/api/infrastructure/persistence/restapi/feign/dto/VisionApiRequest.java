package com.papaolabs.api.infrastructure.persistence.restapi.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisionApiRequest implements Serializable{
    private static final long serialVersionUID = 2222381080708745408L;
    private List<Request> requests;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Image image;
        private List<Feature> features;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Image {
            private Source source;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Source {
                private String imageUri;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Feature {
            private String type;
        }
    }
}
