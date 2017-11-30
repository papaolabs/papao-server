package com.papaolabs.api.interfaces.v1.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.papaolabs.api.infrastructure.persistence.jpa.entity.Post;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class PostPreviewDTO {
    private Long totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private List<Element> elements;

    public static class Element {
        private Long id;
        private Post.PostType postType;
        private Post.StateType stateType;
        private Post.GenderType genderType;
        private List<ImageUrl> imageUrls;
        private String noticeBeginDate;
        private String noticeEndDate;
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
            return new EqualsBuilder()
                .append(id, element.id)
                .append(postType, element.postType)
                .append(stateType, element.stateType)
                .append(genderType, element.genderType)
                .append(imageUrls, element.imageUrls)
                .append(noticeBeginDate, element.noticeBeginDate)
                .append(noticeEndDate, element.noticeEndDate)
                .append(happenDate, element.happenDate)
                .append(happenPlace, element.happenPlace)
                .append(kindName, element.kindName)
                .append(hitCount, element.hitCount)
                .append(commentCount, element.commentCount)
                .append(createdDate, element.createdDate)
                .append(updatedDate, element.updatedDate)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(id)
                .append(postType)
                .append(stateType)
                .append(genderType)
                .append(imageUrls)
                .append(noticeBeginDate)
                .append(noticeEndDate)
                .append(happenDate)
                .append(happenPlace)
                .append(kindName)
                .append(hitCount)
                .append(commentCount)
                .append(createdDate)
                .append(updatedDate)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("id", id)
                .append("postType", postType)
                .append("stateType", stateType)
                .append("genderType", genderType)
                .append("imageUrls", imageUrls)
                .append("noticeBeginDate", noticeBeginDate)
                .append("noticeEndDate", noticeEndDate)
                .append("happenDate", happenDate)
                .append("happenPlace", happenPlace)
                .append("kindName", kindName)
                .append("hitCount", hitCount)
                .append("commentCount", commentCount)
                .append("createdDate", createdDate)
                .append("updatedDate", updatedDate)
                .toString();
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
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
        return new EqualsBuilder()
            .append(totalElements, that.totalElements)
            .append(totalPages, that.totalPages)
            .append(currentPage, that.currentPage)
            .append(elements, that.elements)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(totalElements)
            .append(totalPages)
            .append(currentPage)
            .append(elements)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("totalElements", totalElements)
            .append("totalPages", totalPages)
            .append("currentPage", currentPage)
            .append("elements", elements)
            .toString();
    }
}
