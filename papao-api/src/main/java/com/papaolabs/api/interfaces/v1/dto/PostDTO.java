package com.papaolabs.api.interfaces.v1.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PostDTO {
    private Long id;
    private String type;
    private String imageUrl;
    private String kind;
    private String happenDate;
    private String happenPlace;
    private String contracts;
    private String weight;
    private String gender;
    private String state;
    private String neuter;
    private String feature;
    private String introduction;
}
