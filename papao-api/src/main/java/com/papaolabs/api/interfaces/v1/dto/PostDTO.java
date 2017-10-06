package com.papaolabs.api.interfaces.v1.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PostDTO {
    private Long id;
    private Integer type;
    private String imageUrl;
    private String happenDate;
    private String happenPlace;
    private String kind;
    private String gender;
    private String state;
}
