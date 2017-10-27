package com.papaolabs.client.vision.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VisionApiRequest implements Serializable{
    private static final long serialVersionUID = 2222381080708745408L;
    private List<Request> requests;

    @Data
    public static class Request {
        private Image image;
        private List<Feature> features;

        @Data
        public static class Image {
            private String content;
        }

        @Data
        public static class Feature {
            private String type;
        }
    }
}
