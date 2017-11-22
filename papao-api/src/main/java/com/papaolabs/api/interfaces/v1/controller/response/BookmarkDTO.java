package com.papaolabs.api.interfaces.v1.controller.response;

import java.util.List;

public class BookmarkDTO {
    private Long totalElements;
    private Integer totalPages;
    private List<Element> elements;

    public static class Element {
        private Long userId;
        private Long postId;
        private String createdDate;
        private String updatedDate;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
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
            if (userId != null ? !userId.equals(element.userId) : element.userId != null) {
                return false;
            }
            if (postId != null ? !postId.equals(element.postId) : element.postId != null) {
                return false;
            }
            if (createdDate != null ? !createdDate.equals(element.createdDate) : element.createdDate != null) {
                return false;
            }
            return updatedDate != null ? updatedDate.equals(element.updatedDate) : element.updatedDate == null;
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (postId != null ? postId.hashCode() : 0);
            result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
            result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Element{" +
                "userId=" + userId +
                ", postId=" + postId +
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
