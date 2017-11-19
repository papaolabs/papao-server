package com.papaolabs.push.infrastructure.persistence.jpa.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "push_log_tb")
public class PushLog extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long postId;
    private Long userId;
    private String message;
    @Enumerated(EnumType.STRING)
    private PushType type;

    public enum PushType {
        SEARCH, ALARM, BOOKMARK;

        public static PushType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (PushType type : PushType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return ALARM;
        }
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PushType getType() {
        return type;
    }

    public void setType(PushType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushLog pushLog = (PushLog) o;
        if (id != null ? !id.equals(pushLog.id) : pushLog.id != null) {
            return false;
        }
        if (postId != null ? !postId.equals(pushLog.postId) : pushLog.postId != null) {
            return false;
        }
        if (userId != null ? !userId.equals(pushLog.userId) : pushLog.userId != null) {
            return false;
        }
        if (message != null ? !message.equals(pushLog.message) : pushLog.message != null) {
            return false;
        }
        return type == pushLog.type;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushLog{" +
            "id=" + id +
            ", postId=" + postId +
            ", userId=" + userId +
            ", message='" + message + '\'' +
            ", type=" + type +
            '}';
    }
}
