package com.papaolabs.batch.infrastructure.feign.openapi.dto;

import lombok.Data;

@Data
public class AnimalDTO {
    private String noticeId;
    private String noticeBeginDate;
    private String noticeEndDate;
    private String desertionId;
    private String stateType;
    private String imageUrl;
    private String thumbImageUrl;
    private String breedName;
    private String colorName;
    private String age;
    private String weight;
    private String genderCode;
    private String neuterCode;
    private String jurisdiction;
    private String shelterName;
    private String shelterContact;
    private String shelterAddress;
    private String userName;
    private String userContact;
    private String feature;
    private String note;
    private String happenDate;
    private String happenPlace;
}
