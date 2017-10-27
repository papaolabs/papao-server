package com.papaolabs.client.vision.dto;

import lombok.Data;

@Data
public class VisionLabelResponse {
    private String mid;
    private String description;
    private Double score;
}
