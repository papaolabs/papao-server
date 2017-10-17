package com.papaolabs.api.domain.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "POST_INFO_TB")
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "DESERTION_ID")
    private Long desertionId;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "STATE")
    private String state;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "NEUTER")
    private String neuter;
    @Column(name = "FEATURE")
    private String feature;
    @Column(name = "INTRODUCTION")
    private String introduction;
    @Column(name = "UID")
    private String userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "USER_ADDRESS")
    private String userAddress;
    @Column(name = "USER_CONTACT")
    private String userContact;
    @Column(name = "HAPPEN_DATE")
    private Date happenDate;
    @Column(name = "HAPPEN_PLACE")
    private String happenPlace;
    @Column(name = "UPR_CODE")
    private String uprCode;
    @Column(name = "ORG_CODE")
    private String orgCode;
    @Column(name = "KIND_UP_CODE")
    private String kindUpCode;
    @Column(name = "KIND_CODE")
    private String kindCode;
    @Column(name = "AGE")
    private Integer age;
    @Column(name = "WEIGHT")
    private Float weight;
    @Column(name = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    @LastModifiedDate
    private Date lastModifiedDate;
    @Column(name = "DISPLAY_STATE")
    private Boolean isDisplay;
}
