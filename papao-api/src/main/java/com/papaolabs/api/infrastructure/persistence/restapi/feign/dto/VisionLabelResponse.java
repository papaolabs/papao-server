package com.papaolabs.api.infrastructure.persistence.restapi.feign.dto;

import lombok.Data;

@Data
public class VisionLabelResponse {
    private String mid;
    private String description;
    private Double score;
}
