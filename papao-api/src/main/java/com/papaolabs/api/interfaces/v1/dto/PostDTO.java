package com.papaolabs.api.interfaces.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class PostDTO {
    private Long id;
    private String desertionId;
    private String state;
    private String imageUrl;
    private String type;
    private String gender;
    private String neuter;
    private String feature;
    private String userId;
    private String userName;
    private String userAddress;
    private String userContact;
    private String happenDate;
    private String happenPlace;
    private String kindUpCode;
    private String kindCode;
    private String kindName;
    private String age;
    private String weight;
    // 신규추가
    private Long viewCount;
    private Boolean favorite;
    private String createdDate;
    private String updatedDate;
}
