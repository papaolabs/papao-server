package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment_tb")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long postId;
    private String type;
    private String userId;
    private String userName;
    private String text;
    private Boolean isDisplay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        Comment comment = (Comment) o;
        if (id != null ? !id.equals(comment.id) : comment.id != null) {
            return false;
        }
        if (postId != null ? !postId.equals(comment.postId) : comment.postId != null) {
            return false;
        }
        if (type != null ? !type.equals(comment.type) : comment.type != null) {
            return false;
        }
        if (userId != null ? !userId.equals(comment.userId) : comment.userId != null) {
            return false;
        }
        if (userName != null ? !userName.equals(comment.userName) : comment.userName != null) {
            return false;
        }
        if (text != null ? !text.equals(comment.text) : comment.text != null) {
            return false;
        }
        return isDisplay != null ? isDisplay.equals(comment.isDisplay) : comment.isDisplay == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isDisplay != null ? isDisplay.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", postId=" + postId +
            ", type='" + type + '\'' +
            ", userId='" + userId + '\'' +
            ", userName='" + userName + '\'' +
            ", text='" + text + '\'' +
            ", isDisplay=" + isDisplay +
            '}';
    }
}
