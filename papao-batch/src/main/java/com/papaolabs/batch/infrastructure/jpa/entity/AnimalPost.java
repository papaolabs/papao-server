package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "animal_post_tb")
public class AnimalPost extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private PostType postType;
    private String desertionId;
    private String contact;
    private String noticeId;
    private Date noticeBeginDate;
    private Date noticeEndDate;
    private Date happenDate;
    private String happenPlace;
    private String feature;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private AnimalHelper animalHelper;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Region region;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private AnimalShelter shelter;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private AbandonedAnimal abandonedAnimal;
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="postId")
    private Collection<AnimalImage> image;

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
