package com.papaolabs.api.interfaces.v1.controller.response;

import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;

import java.util.List;
import java.util.Map;

public class PostRankingDTO {
    private String beginDate;
    private String endDate;
    private Map<Post.PostType, List<Element>> elementsMap;

    public static class Element {
        private Long id;
        private Post.PostType postType;
        private Post.StateType stateType;
        private Post.GenderType genderType;
        private List<PostPreviewDTO.Element.ImageUrl> imageUrls;
        private String happenDate;
        private String happenPlace;
        private String kindName;
        private Long hitCount;
        private Integer commentCount;
        private Integer bookmarkCount;
        private Integer score;
        private String createdDate;
        private String updatedDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Post.PostType getPostType() {
            return postType;
        }

        public void setPostType(Post.PostType postType) {
            this.postType = postType;
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

        public List<PostPreviewDTO.Element.ImageUrl> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<PostPreviewDTO.Element.ImageUrl> imageUrls) {
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

        public Integer getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public Integer getBookmarkCount() {
            return bookmarkCount;
        }

        public void setBookmarkCount(Integer bookmarkCount) {
            this.bookmarkCount = bookmarkCount;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
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
            Element element = (Element) o;
            if (id != null ? !id.equals(element.id) : element.id != null) {
                return false;
            }
            if (postType != element.postType) {
                return false;
            }
            if (stateType != element.stateType) {
                return false;
            }
            if (genderType != element.genderType) {
                return false;
            }
            if (imageUrls != null ? !imageUrls.equals(element.imageUrls) : element.imageUrls != null) {
                return false;
            }
            if (happenDate != null ? !happenDate.equals(element.happenDate) : element.happenDate != null) {
                return false;
            }
            if (happenPlace != null ? !happenPlace.equals(element.happenPlace) : element.happenPlace != null) {
                return false;
            }
            if (kindName != null ? !kindName.equals(element.kindName) : element.kindName != null) {
                return false;
            }
            if (hitCount != null ? !hitCount.equals(element.hitCount) : element.hitCount != null) {
                return false;
            }
            if (commentCount != null ? !commentCount.equals(element.commentCount) : element.commentCount != null) {
                return false;
            }
            if (bookmarkCount != null ? !bookmarkCount.equals(element.bookmarkCount) : element.bookmarkCount != null) {
                return false;
            }
            if (score != null ? !score.equals(element.score) : element.score != null) {
                return false;
            }
            if (createdDate != null ? !createdDate.equals(element.createdDate) : element.createdDate != null) {
                return false;
            }
            return updatedDate != null ? updatedDate.equals(element.updatedDate) : element.updatedDate == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (postType != null ? postType.hashCode() : 0);
            result = 31 * result + (stateType != null ? stateType.hashCode() : 0);
            result = 31 * result + (genderType != null ? genderType.hashCode() : 0);
            result = 31 * result + (imageUrls != null ? imageUrls.hashCode() : 0);
            result = 31 * result + (happenDate != null ? happenDate.hashCode() : 0);
            result = 31 * result + (happenPlace != null ? happenPlace.hashCode() : 0);
            result = 31 * result + (kindName != null ? kindName.hashCode() : 0);
            result = 31 * result + (hitCount != null ? hitCount.hashCode() : 0);
            result = 31 * result + (commentCount != null ? commentCount.hashCode() : 0);
            result = 31 * result + (bookmarkCount != null ? bookmarkCount.hashCode() : 0);
            result = 31 * result + (score != null ? score.hashCode() : 0);
            result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
            result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Element{" +
                "id=" + id +
                ", postType=" + postType +
                ", stateType=" + stateType +
                ", genderType=" + genderType +
                ", imageUrls=" + imageUrls +
                ", happenDate='" + happenDate + '\'' +
                ", happenPlace='" + happenPlace + '\'' +
                ", kindName='" + kindName + '\'' +
                ", hitCount=" + hitCount +
                ", commentCount=" + commentCount +
                ", bookmarkCount=" + bookmarkCount +
                ", score=" + score +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
        }
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Map<Post.PostType, List<Element>> getElementsMap() {
        return elementsMap;
    }

    public void setElementsMap(Map<Post.PostType, List<Element>> elementsMap) {
        this.elementsMap = elementsMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostRankingDTO that = (PostRankingDTO) o;
        if (beginDate != null ? !beginDate.equals(that.beginDate) : that.beginDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
            return false;
        }
        return elementsMap != null ? elementsMap.equals(that.elementsMap) : that.elementsMap == null;
    }

    @Override
    public int hashCode() {
        int result = beginDate != null ? beginDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (elementsMap != null ? elementsMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushRankingDTO{" +
            "beginDate='" + beginDate + '\'' +
            ", endDate='" + endDate + '\'' +
            ", elementsMap=" + elementsMap +
            '}';
    }
}
