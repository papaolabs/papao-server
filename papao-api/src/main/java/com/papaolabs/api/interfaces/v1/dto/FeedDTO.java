package com.papaolabs.api.interfaces.v1.dto;

import lombok.Data;

@Data
public class FeedDTO {
    private String noticeNo;
    private String noticeBeginDate;
    private String noticeEndDate;
    private String imageUrl;
    private String thumbImageUrl;
    private String state;
    private String gender;
    private String neuterYn;
    private String description;
    private String shelterName;
    private String shelterTel;
    private String shelterAddress;
    private String department;
    private String managerName;
    private String managerTel;
    private String note;
    private String desertionNo;
    private String happenDate;
    private String happenPlace;
    private String kindCode;
    private String colorCode;
    private String age;
    private String weight;
}
