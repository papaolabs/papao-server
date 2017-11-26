package com.papaolabs.batch.infrastructure.jpa.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post_tb")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String uid;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    @Enumerated(EnumType.STRING)
    private NeuterType neuterType;
    @Enumerated(EnumType.STRING)
    private StateType stateType;
    @Column(unique = true)
    private String desertionId;
    private String noticeId;
    private Date noticeBeginDate;
    private Date noticeEndDate;
    private Date happenDate;
    private Long happenSidoCode;
    private Long happenGunguCode;
    private String happenPlace;
    private String feature;
    private String helperName;
    private String helperContact;
    private Integer age;
    private Float weight;
    private Long hitCount;
    private Long upKindCode;
    private Long kindCode;
    private String kindName;
    private Long shelterCode;
    private String shelterName;
    private String shelterContact;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private List<Comment> comments;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private List<Image> images;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private List<Bookmark> bookmarks;
    private Boolean isDisplay;

    public enum PostType {
        SYSTEM, PROTECTING, ROADREPORT, MISSING, UNKNOWN;

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
            return UNKNOWN;
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

        public String getCode() {
            return code;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Long getHappenSidoCode() {
        return happenSidoCode;
    }

    public void setHappenSidoCode(Long happenSidoCode) {
        this.happenSidoCode = happenSidoCode;
    }

    public Long getHappenGunguCode() {
        return happenGunguCode;
    }

    public void setHappenGunguCode(Long happenGunguCode) {
        this.happenGunguCode = happenGunguCode;
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

    public Long getUpKindCode() {
        return upKindCode;
    }

    public void setUpKindCode(Long upKindCode) {
        this.upKindCode = upKindCode;
    }

    public Long getKindCode() {
        return kindCode;
    }

    public void setKindCode(Long kindCode) {
        this.kindCode = kindCode;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public Long getShelterCode() {
        return shelterCode;
    }

    public void setShelterCode(Long shelterCode) {
        this.shelterCode = shelterCode;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getShelterContact() {
        return shelterContact;
    }

    public void setShelterContact(String shelterContact) {
        this.shelterContact = shelterContact;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Boolean getDisplay() {
        return isDisplay;
    }

    public void setDisplay(Boolean display) {
        isDisplay = display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return new EqualsBuilder()
            .append(id, post.id)
            .append(uid, post.uid)
            .append(postType, post.postType)
            .append(genderType, post.genderType)
            .append(neuterType, post.neuterType)
            .append(stateType, post.stateType)
            .append(desertionId, post.desertionId)
            .append(noticeId, post.noticeId)
            .append(noticeBeginDate, post.noticeBeginDate)
            .append(noticeEndDate, post.noticeEndDate)
            .append(happenDate, post.happenDate)
            .append(happenSidoCode, post.happenSidoCode)
            .append(happenGunguCode, post.happenGunguCode)
            .append(happenPlace, post.happenPlace)
            .append(feature, post.feature)
            .append(helperName, post.helperName)
            .append(helperContact, post.helperContact)
            .append(age, post.age)
            .append(weight, post.weight)
            .append(hitCount, post.hitCount)
            .append(upKindCode, post.upKindCode)
            .append(kindCode, post.kindCode)
            .append(kindName, post.kindName)
            .append(shelterCode, post.shelterCode)
            .append(shelterName, post.shelterName)
            .append(shelterContact, post.shelterContact)
            .append(comments, post.comments)
            .append(images, post.images)
            .append(bookmarks, post.bookmarks)
            .append(isDisplay, post.isDisplay)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(uid)
            .append(postType)
            .append(genderType)
            .append(neuterType)
            .append(stateType)
            .append(desertionId)
            .append(noticeId)
            .append(noticeBeginDate)
            .append(noticeEndDate)
            .append(happenDate)
            .append(happenSidoCode)
            .append(happenGunguCode)
            .append(happenPlace)
            .append(feature)
            .append(helperName)
            .append(helperContact)
            .append(age)
            .append(weight)
            .append(hitCount)
            .append(upKindCode)
            .append(kindCode)
            .append(kindName)
            .append(shelterCode)
            .append(shelterName)
            .append(shelterContact)
            .append(comments)
            .append(images)
            .append(bookmarks)
            .append(isDisplay)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("uid", uid)
            .append("postType", postType)
            .append("genderType", genderType)
            .append("neuterType", neuterType)
            .append("stateType", stateType)
            .append("desertionId", desertionId)
            .append("noticeId", noticeId)
            .append("noticeBeginDate", noticeBeginDate)
            .append("noticeEndDate", noticeEndDate)
            .append("happenDate", happenDate)
            .append("happenSidoCode", happenSidoCode)
            .append("happenGunguCode", happenGunguCode)
            .append("happenPlace", happenPlace)
            .append("feature", feature)
            .append("helperName", helperName)
            .append("helperContact", helperContact)
            .append("age", age)
            .append("weight", weight)
            .append("hitCount", hitCount)
            .append("upKindCode", upKindCode)
            .append("kindCode", kindCode)
            .append("kindName", kindName)
            .append("shelterCode", shelterCode)
            .append("shelterName", shelterName)
            .append("shelterContact", shelterContact)
            .append("comments", comments)
            .append("images", images)
            .append("bookmarks", bookmarks)
            .append("isDisplay", isDisplay)
            .toString();
    }
}
