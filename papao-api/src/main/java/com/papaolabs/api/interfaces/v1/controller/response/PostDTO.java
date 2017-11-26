package com.papaolabs.api.interfaces.v1.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
    private Long commentCount;
    private Long bookmarkCount;
    private String noticeBeginDate;
    private String noticeEndDate;
    private Integer deadlineDay;
    private String userId;

    public static class ImageUrl {
        private Long key;
        private String url;

        public Long getKey() {
            return key;
        }

        public void setKey(Long key) {
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ImageUrl imageUrl = (ImageUrl) o;
            if (key != null ? !key.equals(imageUrl.key) : imageUrl.key != null) {
                return false;
            }
            return url != null ? url.equals(imageUrl.url) : imageUrl.url == null;
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (url != null ? url.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ImageUrl{" +
                "key=" + key +
                ", url='" + url + '\'' +
                '}';
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesertionId() {
        return desertionId;
    }

    public void setDesertionId(String desertionId) {
        this.desertionId = desertionId;
    }

    public Post.StateType getStateType() {
        return stateType;
    }

    public void setStateType(Post.StateType stateType) {
        this.stateType = stateType;
    }

    public Post.PostType getPostType() {
        return postType;
    }

    public void setPostType(Post.PostType postType) {
        this.postType = postType;
    }

    public Post.GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(Post.GenderType genderType) {
        this.genderType = genderType;
    }

    public Post.NeuterType getNeuterType() {
        return neuterType;
    }

    public void setNeuterType(Post.NeuterType neuterType) {
        this.neuterType = neuterType;
    }

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageUrl> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }

    public String getManagerContact() {
        return managerContact;
    }

    public void setManagerContact(String managerContact) {
        this.managerContact = managerContact;
    }

    public String getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(String happenDate) {
        this.happenDate = happenDate;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getUpKindName() {
        return upKindName;
    }

    public void setUpKindName(String upKindName) {
        this.upKindName = upKindName;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getGunguName() {
        return gunguName;
    }

    public void setGunguName(String gunguName) {
        this.gunguName = gunguName;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(Long bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public String getNoticeBeginDate() {
        return noticeBeginDate;
    }

    public void setNoticeBeginDate(String noticeBeginDate) {
        this.noticeBeginDate = noticeBeginDate;
    }

    public String getNoticeEndDate() {
        return noticeEndDate;
    }

    public void setNoticeEndDate(String noticeEndDate) {
        this.noticeEndDate = noticeEndDate;
    }

    public Integer getDeadlineDay() {
        return deadlineDay;
    }

    public void setDeadlineDay(Integer deadlineDay) {
        this.deadlineDay = deadlineDay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostDTO postDTO = (PostDTO) o;
        return new EqualsBuilder()
            .append(id, postDTO.id)
            .append(desertionId, postDTO.desertionId)
            .append(stateType, postDTO.stateType)
            .append(postType, postDTO.postType)
            .append(genderType, postDTO.genderType)
            .append(neuterType, postDTO.neuterType)
            .append(imageUrls, postDTO.imageUrls)
            .append(feature, postDTO.feature)
            .append(shelterName, postDTO.shelterName)
            .append(managerId, postDTO.managerId)
            .append(managerName, postDTO.managerName)
            .append(managerAddress, postDTO.managerAddress)
            .append(managerContact, postDTO.managerContact)
            .append(happenDate, postDTO.happenDate)
            .append(happenPlace, postDTO.happenPlace)
            .append(upKindName, postDTO.upKindName)
            .append(kindName, postDTO.kindName)
            .append(sidoName, postDTO.sidoName)
            .append(gunguName, postDTO.gunguName)
            .append(age, postDTO.age)
            .append(weight, postDTO.weight)
            .append(hitCount, postDTO.hitCount)
            .append(createdDate, postDTO.createdDate)
            .append(updatedDate, postDTO.updatedDate)
            .append(commentCount, postDTO.commentCount)
            .append(bookmarkCount, postDTO.bookmarkCount)
            .append(noticeBeginDate, postDTO.noticeBeginDate)
            .append(noticeEndDate, postDTO.noticeEndDate)
            .append(deadlineDay, postDTO.deadlineDay)
            .append(userId, postDTO.userId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(desertionId)
            .append(stateType)
            .append(postType)
            .append(genderType)
            .append(neuterType)
            .append(imageUrls)
            .append(feature)
            .append(shelterName)
            .append(managerId)
            .append(managerName)
            .append(managerAddress)
            .append(managerContact)
            .append(happenDate)
            .append(happenPlace)
            .append(upKindName)
            .append(kindName)
            .append(sidoName)
            .append(gunguName)
            .append(age)
            .append(weight)
            .append(hitCount)
            .append(createdDate)
            .append(updatedDate)
            .append(commentCount)
            .append(bookmarkCount)
            .append(noticeBeginDate)
            .append(noticeEndDate)
            .append(deadlineDay)
            .append(userId)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("desertionId", desertionId)
            .append("stateType", stateType)
            .append("postType", postType)
            .append("genderType", genderType)
            .append("neuterType", neuterType)
            .append("imageUrls", imageUrls)
            .append("feature", feature)
            .append("shelterName", shelterName)
            .append("managerId", managerId)
            .append("managerName", managerName)
            .append("managerAddress", managerAddress)
            .append("managerContact", managerContact)
            .append("happenDate", happenDate)
            .append("happenPlace", happenPlace)
            .append("upKindName", upKindName)
            .append("kindName", kindName)
            .append("sidoName", sidoName)
            .append("gunguName", gunguName)
            .append("age", age)
            .append("weight", weight)
            .append("hitCount", hitCount)
            .append("createdDate", createdDate)
            .append("updatedDate", updatedDate)
            .append("commentCount", commentCount)
            .append("bookmarkCount", bookmarkCount)
            .append("noticeBeginDate", noticeBeginDate)
            .append("noticeEndDate", noticeEndDate)
            .append("deadlineDay", deadlineDay)
            .append("userId", userId)
            .toString();
    }
}
