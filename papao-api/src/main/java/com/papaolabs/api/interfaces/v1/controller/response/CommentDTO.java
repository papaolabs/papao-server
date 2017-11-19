package com.papaolabs.api.interfaces.v1.controller.response;

import java.util.List;

public class CommentDTO {
    private Long postId;
    private List<Content> contents;

    public static class Content {
        private Long id;
        private String userId;
        private String nickname;
        private String profileUrl;
        private String text;
        private String createdDate;
        private String lastModifiedDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(String lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Content content = (Content) o;
            if (id != null ? !id.equals(content.id) : content.id != null) {
                return false;
            }
            if (userId != null ? !userId.equals(content.userId) : content.userId != null) {
                return false;
            }
            if (nickname != null ? !nickname.equals(content.nickname) : content.nickname != null) {
                return false;
            }
            if (profileUrl != null ? !profileUrl.equals(content.profileUrl) : content.profileUrl != null) {
                return false;
            }
            if (text != null ? !text.equals(content.text) : content.text != null) {
                return false;
            }
            if (createdDate != null ? !createdDate.equals(content.createdDate) : content.createdDate != null) {
                return false;
            }
            return lastModifiedDate != null ? lastModifiedDate.equals(content.lastModifiedDate) : content.lastModifiedDate == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (userId != null ? userId.hashCode() : 0);
            result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
            result = 31 * result + (profileUrl != null ? profileUrl.hashCode() : 0);
            result = 31 * result + (text != null ? text.hashCode() : 0);
            result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
            result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Content{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", text='" + text + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                '}';
        }
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentDTO that = (CommentDTO) o;
        if (postId != null ? !postId.equals(that.postId) : that.postId != null) {
            return false;
        }
        return contents != null ? contents.equals(that.contents) : that.contents == null;
    }

    @Override
    public int hashCode() {
        int result = postId != null ? postId.hashCode() : 0;
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "postId=" + postId +
            ", contents=" + contents +
            '}';
    }
}
