package com.papaolabs.push.domain.model;

import org.apache.commons.lang3.StringUtils;

public class PushRequest {
    private String userId;
    private String message;
    private PushType type;

    public enum PushType {
        SEARCH, ALARM, POST;

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
        PushRequest that = (PushRequest) o;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushRequest{" +
            "userId='" + userId + '\'' +
            ", message='" + message + '\'' +
            ", type=" + type +
            '}';
    }
}
