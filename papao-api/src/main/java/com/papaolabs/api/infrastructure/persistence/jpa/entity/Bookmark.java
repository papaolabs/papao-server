package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bookmark_tb")
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bookmark bookmark = (Bookmark) o;
        if (id != null ? !id.equals(bookmark.id) : bookmark.id != null) {
            return false;
        }
        if (userId != null ? !userId.equals(bookmark.userId) : bookmark.userId != null) {
            return false;
        }
        return postId != null ? postId.equals(bookmark.postId) : bookmark.postId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
            "id=" + id +
            ", userId=" + userId +
            ", postId=" + postId +
            '}';
    }
}
