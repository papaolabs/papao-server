package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "animal_post_tb")
public class AnimalPost extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long helperId;
    private Long regionId;
    private PostType postType;
    private String contact;
    private String desertionId;
    private String noticeId;
    private Date noticeBeginDate;
    private Date noticeEndDate;
    private Date happenDate;
    private String happenPlace;
    private String feature;

    public enum PostType {
        SYSTEM("01"), ABSENCE("02"), PROTECT("03");
        private String code;

        PostType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
