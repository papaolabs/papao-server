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
    private String userId;
    private String text;
    private Boolean isDisplay;
    private Long postId;

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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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
        if (userId != null ? !userId.equals(comment.userId) : comment.userId != null) {
            return false;
        }
        if (text != null ? !text.equals(comment.text) : comment.text != null) {
            return false;
        }
        if (isDisplay != null ? !isDisplay.equals(comment.isDisplay) : comment.isDisplay != null) {
            return false;
        }
        return postId != null ? postId.equals(comment.postId) : comment.postId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isDisplay != null ? isDisplay.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", userId='" + userId + '\'' +
            ", text='" + text + '\'' +
            ", isDisplay=" + isDisplay +
            ", postId=" + postId +
            '}';
    }
}
