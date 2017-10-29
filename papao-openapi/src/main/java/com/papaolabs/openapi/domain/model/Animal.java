package com.papaolabs.openapi.domain.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Animal {
    private String noticeId;
    private String noticeBeginDate;
    private String noticeEndDate;
    private Long desertionId;
    private String stateType;
    private String imageUrl;
    private String thumbImageUrl;
    private String breedName;
    private String colorName;
    private String age;
    private Float weight;
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
/*
    Todo : 무조건 null 발생함
    private Integer pageSize;
    private Integer pageIndex;
    private Integer pageTotalCount;
    private Integer resultCode;
    private String resultMessage;
*/
    private String happenDate;
    private String happenPlace;
}
