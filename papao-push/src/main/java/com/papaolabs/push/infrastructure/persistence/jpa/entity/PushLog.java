package com.papaolabs.push.infrastructure.persistence.jpa.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    private String userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
        return new EqualsBuilder()
            .append(id, pushLog.id)
            .append(postId, pushLog.postId)
            .append(userId, pushLog.userId)
            .append(message, pushLog.message)
            .append(type, pushLog.type)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(postId)
            .append(userId)
            .append(message)
            .append(type)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("postId", postId)
            .append("userId", userId)
            .append("message", message)
            .append("type", type)
            .toString();
    }
}
