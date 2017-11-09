package com.papaolabs.batch.infrastructure.jpa.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "post_tb")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    @Enumerated(EnumType.STRING)
    private NeuterType neuterType;
    @Enumerated(EnumType.STRING)
    private StateType stateType;
    private String desertionId;
    private String noticeId;
    private Date noticeBeginDate;
    private Date noticeEndDate;
    private Date happenDate;
    private String happenPlace;
    private String feature;
    private String helperName;
    private String helperContact;
    private Integer age;
    private Float weight;
    private Long hitCount;
    @OneToOne
    @JoinColumn(name = "kindCode")
    private Breed Breed;
    @OneToOne
    @JoinColumn(name = "shelterCode")
    private Shelter Shelter;
    private String shelterContact;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private Collection<Image> images;
    private Boolean isDisplay;

    public enum PostType {
        SYSTEM, PROTECTING, ROADREPORT, MISSING;

        public static PostType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (PostType type : PostType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return SYSTEM;
        }
    }

    public enum GenderType {
        M, F, U;

        public static GenderType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (GenderType type : GenderType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return U;
        }
    }

    public enum NeuterType {
        Y, N, U;

        public static NeuterType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (NeuterType type : NeuterType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return U;
        }
    }

    public enum StateType {
        PROCESS("보호중"), RETURN("종료(반환)"), NATURALDEATH("종료(자연사)"), EUTHANASIA("종료(안락사)"), ADOPTION("종료(입양)"), UNKNOWN("알수없음");
        private final String code;

        StateType(String code) {
            this.code = code;
        }

        public static StateType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (StateType type : StateType.values()) {
                if (type.code.equals(name)) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public NeuterType getNeuterType() {
        return neuterType;
    }

    public void setNeuterType(NeuterType neuterType) {
        this.neuterType = neuterType;
    }

    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    public String getDesertionId() {
        return desertionId;
    }

    public void setDesertionId(String desertionId) {
        this.desertionId = desertionId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public Date getNoticeBeginDate() {
        return noticeBeginDate;
    }

    public void setNoticeBeginDate(Date noticeBeginDate) {
        this.noticeBeginDate = noticeBeginDate;
    }

    public Date getNoticeEndDate() {
        return noticeEndDate;
    }

    public void setNoticeEndDate(Date noticeEndDate) {
        this.noticeEndDate = noticeEndDate;
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public String getHelperContact() {
        return helperContact;
    }

    public void setHelperContact(String helperContact) {
        this.helperContact = helperContact;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }

    public com.papaolabs.batch.infrastructure.jpa.entity.Breed getBreed() {
        return Breed;
    }

    public void setBreed(com.papaolabs.batch.infrastructure.jpa.entity.Breed breed) {
        Breed = breed;
    }

    public com.papaolabs.batch.infrastructure.jpa.entity.Shelter getShelter() {
        return Shelter;
    }

    public void setShelter(com.papaolabs.batch.infrastructure.jpa.entity.Shelter shelter) {
        Shelter = shelter;
    }

    public String getShelterContact() {
        return shelterContact;
    }

    public void setShelterContact(String shelterContact) {
        this.shelterContact = shelterContact;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    public Boolean getDisplay() {
        return isDisplay;
    }

    public void setDisplay(Boolean display) {
        isDisplay = display;
    }
}
