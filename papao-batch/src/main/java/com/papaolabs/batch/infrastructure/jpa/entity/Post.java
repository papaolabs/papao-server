package com.papaolabs.batch.infrastructure.jpa.entity;

import org.apache.commons.lang3.StringUtils;

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
    private Long uid;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
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
        if (id != null ? !id.equals(post.id) : post.id != null) {
            return false;
        }
        if (uid != null ? !uid.equals(post.uid) : post.uid != null) {
            return false;
        }
        if (postType != post.postType) {
            return false;
        }
        if (genderType != post.genderType) {
            return false;
        }
        if (neuterType != post.neuterType) {
            return false;
        }
        if (stateType != post.stateType) {
            return false;
        }
        if (desertionId != null ? !desertionId.equals(post.desertionId) : post.desertionId != null) {
            return false;
        }
        if (noticeId != null ? !noticeId.equals(post.noticeId) : post.noticeId != null) {
            return false;
        }
        if (noticeBeginDate != null ? !noticeBeginDate.equals(post.noticeBeginDate) : post.noticeBeginDate != null) {
            return false;
        }
        if (noticeEndDate != null ? !noticeEndDate.equals(post.noticeEndDate) : post.noticeEndDate != null) {
            return false;
        }
        if (happenDate != null ? !happenDate.equals(post.happenDate) : post.happenDate != null) {
            return false;
        }
        if (happenSidoCode != null ? !happenSidoCode.equals(post.happenSidoCode) : post.happenSidoCode != null) {
            return false;
        }
        if (happenGunguCode != null ? !happenGunguCode.equals(post.happenGunguCode) : post.happenGunguCode != null) {
            return false;
        }
        if (happenPlace != null ? !happenPlace.equals(post.happenPlace) : post.happenPlace != null) {
            return false;
        }
        if (feature != null ? !feature.equals(post.feature) : post.feature != null) {
            return false;
        }
        if (helperName != null ? !helperName.equals(post.helperName) : post.helperName != null) {
            return false;
        }
        if (helperContact != null ? !helperContact.equals(post.helperContact) : post.helperContact != null) {
            return false;
        }
        if (age != null ? !age.equals(post.age) : post.age != null) {
            return false;
        }
        if (weight != null ? !weight.equals(post.weight) : post.weight != null) {
            return false;
        }
        if (hitCount != null ? !hitCount.equals(post.hitCount) : post.hitCount != null) {
            return false;
        }
        if (upKindCode != null ? !upKindCode.equals(post.upKindCode) : post.upKindCode != null) {
            return false;
        }
        if (kindCode != null ? !kindCode.equals(post.kindCode) : post.kindCode != null) {
            return false;
        }
        if (kindName != null ? !kindName.equals(post.kindName) : post.kindName != null) {
            return false;
        }
        if (shelterCode != null ? !shelterCode.equals(post.shelterCode) : post.shelterCode != null) {
            return false;
        }
        if (shelterName != null ? !shelterName.equals(post.shelterName) : post.shelterName != null) {
            return false;
        }
        if (shelterContact != null ? !shelterContact.equals(post.shelterContact) : post.shelterContact != null) {
            return false;
        }
        if (comments != null ? !comments.equals(post.comments) : post.comments != null) {
            return false;
        }
        if (images != null ? !images.equals(post.images) : post.images != null) {
            return false;
        }
        if (bookmarks != null ? !bookmarks.equals(post.bookmarks) : post.bookmarks != null) {
            return false;
        }
        return isDisplay != null ? isDisplay.equals(post.isDisplay) : post.isDisplay == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (postType != null ? postType.hashCode() : 0);
        result = 31 * result + (genderType != null ? genderType.hashCode() : 0);
        result = 31 * result + (neuterType != null ? neuterType.hashCode() : 0);
        result = 31 * result + (stateType != null ? stateType.hashCode() : 0);
        result = 31 * result + (desertionId != null ? desertionId.hashCode() : 0);
        result = 31 * result + (noticeId != null ? noticeId.hashCode() : 0);
        result = 31 * result + (noticeBeginDate != null ? noticeBeginDate.hashCode() : 0);
        result = 31 * result + (noticeEndDate != null ? noticeEndDate.hashCode() : 0);
        result = 31 * result + (happenDate != null ? happenDate.hashCode() : 0);
        result = 31 * result + (happenSidoCode != null ? happenSidoCode.hashCode() : 0);
        result = 31 * result + (happenGunguCode != null ? happenGunguCode.hashCode() : 0);
        result = 31 * result + (happenPlace != null ? happenPlace.hashCode() : 0);
        result = 31 * result + (feature != null ? feature.hashCode() : 0);
        result = 31 * result + (helperName != null ? helperName.hashCode() : 0);
        result = 31 * result + (helperContact != null ? helperContact.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (hitCount != null ? hitCount.hashCode() : 0);
        result = 31 * result + (upKindCode != null ? upKindCode.hashCode() : 0);
        result = 31 * result + (kindCode != null ? kindCode.hashCode() : 0);
        result = 31 * result + (kindName != null ? kindName.hashCode() : 0);
        result = 31 * result + (shelterCode != null ? shelterCode.hashCode() : 0);
        result = 31 * result + (shelterName != null ? shelterName.hashCode() : 0);
        result = 31 * result + (shelterContact != null ? shelterContact.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (bookmarks != null ? bookmarks.hashCode() : 0);
        result = 31 * result + (isDisplay != null ? isDisplay.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + id +
            ", uid=" + uid +
            ", postType=" + postType +
            ", genderType=" + genderType +
            ", neuterType=" + neuterType +
            ", stateType=" + stateType +
            ", desertionId='" + desertionId + '\'' +
            ", noticeId='" + noticeId + '\'' +
            ", noticeBeginDate=" + noticeBeginDate +
            ", noticeEndDate=" + noticeEndDate +
            ", happenDate=" + happenDate +
            ", happenSidoCode=" + happenSidoCode +
            ", happenGunguCode=" + happenGunguCode +
            ", happenPlace='" + happenPlace + '\'' +
            ", feature='" + feature + '\'' +
            ", helperName='" + helperName + '\'' +
            ", helperContact='" + helperContact + '\'' +
            ", age=" + age +
            ", weight=" + weight +
            ", hitCount=" + hitCount +
            ", upKindCode=" + upKindCode +
            ", kindCode=" + kindCode +
            ", kindName='" + kindName + '\'' +
            ", shelterCode=" + shelterCode +
            ", shelterName='" + shelterName + '\'' +
            ", shelterContact='" + shelterContact + '\'' +
            ", comments=" + comments +
            ", images=" + images +
            ", bookmarks=" + bookmarks +
            ", isDisplay=" + isDisplay +
            '}';
    }
}
