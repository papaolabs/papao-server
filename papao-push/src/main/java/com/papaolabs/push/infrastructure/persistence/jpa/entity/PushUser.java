package com.papaolabs.push.infrastructure.persistence.jpa.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "push_user_tb")
public class PushUser extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    private UserType type;
    private Long userId;
    private String deviceId;

    public enum UserType {
        USER, GUEST;

        public static UserType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (UserType type : UserType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return GUEST;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushUser pushUser = (PushUser) o;
        if (id != null ? !id.equals(pushUser.id) : pushUser.id != null) {
            return false;
        }
        if (type != pushUser.type) {
            return false;
        }
        if (userId != null ? !userId.equals(pushUser.userId) : pushUser.userId != null) {
            return false;
        }
        return deviceId != null ? deviceId.equals(pushUser.deviceId) : pushUser.deviceId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushUser{" +
            "id=" + id +
            ", type=" + type +
            ", userId=" + userId +
            ", deviceId='" + deviceId + '\'' +
            '}';
    }
}
