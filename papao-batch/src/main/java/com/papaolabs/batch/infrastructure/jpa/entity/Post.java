package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

@Data
public class Post {
    private String noticeId;
    private String noticeBeginDate;
    private String noticeEndDate;
    private String desertionId;
    private String stateType;
    private String imageUrl;
    private Long animalCode;
    private String colorName;
    private Integer age;
    private Float weight;
    private String genderCode;
    private String neuterCode;
    private Long shelterCode;
    private String shelterContact;
    private String manager;
    private String contact;
    private String feature;
    private String happenDate;
    private String happenPlace;
}
