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
@Table(name = "FEED_INFO_TB")
public class Feed {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "NOTICE_BEGIN_DATE")
    private Date noticeBeginDate;
    @Column(name = "NOTICE_END_DATE")
    private Date noticeEndDate;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "THUMB_IMAGE_URL")
    private String thumbImageUrl;
    @Column(name = "STATE")
    private String state;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "NEUTER_YN")
    private String neuterYn;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "SHELTER_NAME")
    private String shelterName;
    @Column(name = "SHELTER_TEL")
    private String shelterTel;
    @Column(name = "SHELTER_ADDRESS")
    private String shelterAddress;
    @Column(name = "DEPARTMENT")
    private String department;
    @Column(name = "MANAGER_NAME")
    private String managerName;
    @Column(name = "MANAGER_TEL")
    private String managerTel;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "DESERTION_NO")
    private String desertionNo;
    @Column(name = "HAPPEN_DATE")
    private Date happenDate;
    @Column(name = "HAPPEN_PLACE")
    private Date happenPlace;
    @Column(name = "KIND_CODE")
    private String kindCode;
    @Column(name = "COLOR_CODE")
    private String colorCode;
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
}
