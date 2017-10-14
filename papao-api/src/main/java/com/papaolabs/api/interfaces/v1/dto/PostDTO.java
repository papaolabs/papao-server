package com.papaolabs.api.interfaces.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Getter
@Value
@JsonInclude(NON_NULL)
public class PostDTO {
    private Long id;
    private String type;
    private String imageUrl;
    private String kindUpCode;
    private String kindCode;
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
