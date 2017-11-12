package com.papaolabs.push.infrastructure.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "push_log_tb")
public class PushLog extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (userId != null ? !userId.equals(pushLog.userId) : pushLog.userId != null) {
            return false;
        }
        return message != null ? message.equals(pushLog.message) : pushLog.message == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushLog{" +
            "id=" + id +
            ", userId=" + userId +
            ", message='" + message + '\'' +
            '}';
    }
}
