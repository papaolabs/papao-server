package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "post_info_tb")
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String noticeId;
    private Date noticeBeginDate;
    private Date noticeEndDate;
    private String desertionId;
    private String postType;
    private String stateType;
    private String imageUrl;
    private Long animalCode;
    private Integer age;
    private Float weight;
    private String genderCode;
    private String neuterCode;
    private Long shelterCode;
    private String shelterContact;
    private String manager;
    private String contact;
    private String feature;
    private Date happenDate;
    private String happenPlace;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;
    private Boolean isDisplay;
}
