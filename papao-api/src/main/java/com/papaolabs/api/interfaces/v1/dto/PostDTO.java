package com.papaolabs.api.interfaces.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class PostDTO {
    private Long id;
    private String desertionId;
    private Post.StateType stateType;
    private Post.PostType postType;
    private Post.GenderType genderType;
    private Post.NeuterType neuterType;
    private List<ImageUrl> imageUrls;
    private String feature;
    private String shelterName;
    private String managerId;
    private String managerName;
    private String managerAddress;
    private String managerContact;
    private String happenDate;
    private String happenPlace;
    private String upKindName;
    private String kindName;
    private String sidoName;
    private String gunguName;
    private Integer age;
    private Float weight;
    // 신규추가
    private Long hitCount;
    private String createdDate;
    private String updatedDate;

    @Data
    public static class ImageUrl {
        private Long key;
        private String url;
    }
}
