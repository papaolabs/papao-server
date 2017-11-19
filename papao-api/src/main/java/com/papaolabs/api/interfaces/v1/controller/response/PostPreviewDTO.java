package com.papaolabs.api.interfaces.v1.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class PostPreviewDTO {
    private Long totalElements;
    private Integer totalPages;
    private List<Element> elements;

    public static class Element {
        private Long id;
        private Post.PostType postType;
        private Post.StateType stateType;
        private Post.GenderType genderType;
        private List<ImageUrl> imageUrls;
        private String happenDate;
        private String happenPlace;
        private String kindName;
        private Long hitCount;
        private Integer commentCount;
        private String createdDate;
        private String updatedDate;

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

        public Integer getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Integer commentCount) {
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
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
        }
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
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
        if (totalElements != null ? !totalElements.equals(that.totalElements) : that.totalElements != null) {
            return false;
        }
        if (totalPages != null ? !totalPages.equals(that.totalPages) : that.totalPages != null) {
            return false;
        }
        return elements != null ? elements.equals(that.elements) : that.elements == null;
    }

    @Override
    public int hashCode() {
        int result = totalElements != null ? totalElements.hashCode() : 0;
        result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostPreviewDTO{" +
            "totalElements=" + totalElements +
            ", totalPages=" + totalPages +
            ", elements=" + elements +
            '}';
    }
}
