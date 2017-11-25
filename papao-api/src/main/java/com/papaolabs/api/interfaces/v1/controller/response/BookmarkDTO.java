package com.papaolabs.api.interfaces.v1.controller.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class BookmarkDTO {
    private Long totalElements;
    private Integer totalPages;
    private List<Element> elements;

    public static class Element {
        private String userId;
        private Long postId;
        private String createdDate;
        private String updatedDate;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
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
                .append(userId, element.userId)
                .append(postId, element.postId)
                .append(createdDate, element.createdDate)
                .append(updatedDate, element.updatedDate)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(postId)
                .append(createdDate)
                .append(updatedDate)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("userId", userId)
                .append("postId", postId)
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
        BookmarkDTO that = (BookmarkDTO) o;
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
        return "BookmarkDTO{" +
            "totalElements=" + totalElements +
            ", totalPages=" + totalPages +
            ", elements=" + elements +
            '}';
    }
}
