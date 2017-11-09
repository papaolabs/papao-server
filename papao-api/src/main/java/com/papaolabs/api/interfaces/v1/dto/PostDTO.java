package com.papaolabs.api.interfaces.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import lombok.Data;

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

    @Data
    public static class ImageUrl {
        private Long key;
        private String url;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostDTO postDTO = (PostDTO) o;
        if (id != null ? !id.equals(postDTO.id) : postDTO.id != null) {
            return false;
        }
        if (desertionId != null ? !desertionId.equals(postDTO.desertionId) : postDTO.desertionId != null) {
            return false;
        }
        if (stateType != postDTO.stateType) {
            return false;
        }
        if (postType != postDTO.postType) {
            return false;
        }
        if (genderType != postDTO.genderType) {
            return false;
        }
        if (neuterType != postDTO.neuterType) {
            return false;
        }
        if (imageUrls != null ? !imageUrls.equals(postDTO.imageUrls) : postDTO.imageUrls != null) {
            return false;
        }
        if (feature != null ? !feature.equals(postDTO.feature) : postDTO.feature != null) {
            return false;
        }
        if (shelterName != null ? !shelterName.equals(postDTO.shelterName) : postDTO.shelterName != null) {
            return false;
        }
        if (managerId != null ? !managerId.equals(postDTO.managerId) : postDTO.managerId != null) {
            return false;
        }
        if (managerName != null ? !managerName.equals(postDTO.managerName) : postDTO.managerName != null) {
            return false;
        }
        if (managerAddress != null ? !managerAddress.equals(postDTO.managerAddress) : postDTO.managerAddress != null) {
            return false;
        }
        if (managerContact != null ? !managerContact.equals(postDTO.managerContact) : postDTO.managerContact != null) {
            return false;
        }
        if (happenDate != null ? !happenDate.equals(postDTO.happenDate) : postDTO.happenDate != null) {
            return false;
        }
        if (happenPlace != null ? !happenPlace.equals(postDTO.happenPlace) : postDTO.happenPlace != null) {
            return false;
        }
        if (upKindName != null ? !upKindName.equals(postDTO.upKindName) : postDTO.upKindName != null) {
            return false;
        }
        if (kindName != null ? !kindName.equals(postDTO.kindName) : postDTO.kindName != null) {
            return false;
        }
        if (sidoName != null ? !sidoName.equals(postDTO.sidoName) : postDTO.sidoName != null) {
            return false;
        }
        if (gunguName != null ? !gunguName.equals(postDTO.gunguName) : postDTO.gunguName != null) {
            return false;
        }
        if (age != null ? !age.equals(postDTO.age) : postDTO.age != null) {
            return false;
        }
        if (weight != null ? !weight.equals(postDTO.weight) : postDTO.weight != null) {
            return false;
        }
        if (hitCount != null ? !hitCount.equals(postDTO.hitCount) : postDTO.hitCount != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(postDTO.createdDate) : postDTO.createdDate != null) {
            return false;
        }
        if (updatedDate != null ? !updatedDate.equals(postDTO.updatedDate) : postDTO.updatedDate != null) {
            return false;
        }
        return commentCount != null ? commentCount.equals(postDTO.commentCount) : postDTO.commentCount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (desertionId != null ? desertionId.hashCode() : 0);
        result = 31 * result + (stateType != null ? stateType.hashCode() : 0);
        result = 31 * result + (postType != null ? postType.hashCode() : 0);
        result = 31 * result + (genderType != null ? genderType.hashCode() : 0);
        result = 31 * result + (neuterType != null ? neuterType.hashCode() : 0);
        result = 31 * result + (imageUrls != null ? imageUrls.hashCode() : 0);
        result = 31 * result + (feature != null ? feature.hashCode() : 0);
        result = 31 * result + (shelterName != null ? shelterName.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (managerName != null ? managerName.hashCode() : 0);
        result = 31 * result + (managerAddress != null ? managerAddress.hashCode() : 0);
        result = 31 * result + (managerContact != null ? managerContact.hashCode() : 0);
        result = 31 * result + (happenDate != null ? happenDate.hashCode() : 0);
        result = 31 * result + (happenPlace != null ? happenPlace.hashCode() : 0);
        result = 31 * result + (upKindName != null ? upKindName.hashCode() : 0);
        result = 31 * result + (kindName != null ? kindName.hashCode() : 0);
        result = 31 * result + (sidoName != null ? sidoName.hashCode() : 0);
        result = 31 * result + (gunguName != null ? gunguName.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (hitCount != null ? hitCount.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (commentCount != null ? commentCount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + id +
            ", desertionId='" + desertionId + '\'' +
            ", stateType=" + stateType +
            ", postType=" + postType +
            ", genderType=" + genderType +
            ", neuterType=" + neuterType +
            ", imageUrls=" + imageUrls +
            ", feature='" + feature + '\'' +
            ", shelterName='" + shelterName + '\'' +
            ", managerId='" + managerId + '\'' +
            ", managerName='" + managerName + '\'' +
            ", managerAddress='" + managerAddress + '\'' +
            ", managerContact='" + managerContact + '\'' +
            ", happenDate='" + happenDate + '\'' +
            ", happenPlace='" + happenPlace + '\'' +
            ", upKindName='" + upKindName + '\'' +
            ", kindName='" + kindName + '\'' +
            ", sidoName='" + sidoName + '\'' +
            ", gunguName='" + gunguName + '\'' +
            ", age=" + age +
            ", weight=" + weight +
            ", hitCount=" + hitCount +
            ", createdDate='" + createdDate + '\'' +
            ", updatedDate='" + updatedDate + '\'' +
            ", commentCount=" + commentCount +
            '}';
    }
}
