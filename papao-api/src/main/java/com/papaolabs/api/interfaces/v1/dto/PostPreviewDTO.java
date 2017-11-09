package com.papaolabs.api.interfaces.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class PostPreviewDTO {
    private Long id;
    private Post.StateType stateType;
    private Post.GenderType genderType;
    private List<ImageUrl> imageUrls;
    private String happenDate;
    private String happenPlace;
    private String kindName;
    private Long hitCount;
    private Long commentCount;
    private String createdDate;
    private String updatedDate;

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

    public Post.StateType getStateType() {
        return stateType;
    }

    public void setStateType(Post.StateType stateType) {
        this.stateType = stateType;
    }

    public Post.GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(Post.GenderType genderType) {
        this.genderType = genderType;
    }

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageUrl> imageUrls) {
        this.imageUrls = imageUrls;
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

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostPreviewDTO that = (PostPreviewDTO) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (stateType != that.stateType) {
            return false;
        }
        if (genderType != that.genderType) {
            return false;
        }
        if (imageUrls != null ? !imageUrls.equals(that.imageUrls) : that.imageUrls != null) {
            return false;
        }
        if (happenDate != null ? !happenDate.equals(that.happenDate) : that.happenDate != null) {
            return false;
        }
        if (happenPlace != null ? !happenPlace.equals(that.happenPlace) : that.happenPlace != null) {
            return false;
        }
        if (kindName != null ? !kindName.equals(that.kindName) : that.kindName != null) {
            return false;
        }
        if (hitCount != null ? !hitCount.equals(that.hitCount) : that.hitCount != null) {
            return false;
        }
        if (commentCount != null ? !commentCount.equals(that.commentCount) : that.commentCount != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) {
            return false;
        }
        return updatedDate != null ? updatedDate.equals(that.updatedDate) : that.updatedDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stateType != null ? stateType.hashCode() : 0);
        result = 31 * result + (genderType != null ? genderType.hashCode() : 0);
        result = 31 * result + (imageUrls != null ? imageUrls.hashCode() : 0);
        result = 31 * result + (happenDate != null ? happenDate.hashCode() : 0);
        result = 31 * result + (happenPlace != null ? happenPlace.hashCode() : 0);
        result = 31 * result + (kindName != null ? kindName.hashCode() : 0);
        result = 31 * result + (hitCount != null ? hitCount.hashCode() : 0);
        result = 31 * result + (commentCount != null ? commentCount.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostPreviewDTO{" +
            "id=" + id +
            ", stateType=" + stateType +
            ", genderType=" + genderType +
            ", imageUrls=" + imageUrls +
            ", happenDate='" + happenDate + '\'' +
            ", happenPlace='" + happenPlace + '\'' +
            ", kindName='" + kindName + '\'' +
            ", hitCount=" + hitCount +
            ", commentCount=" + commentCount +
            ", createdDate='" + createdDate + '\'' +
            ", updatedDate='" + updatedDate + '\'' +
            '}';
    }
}
