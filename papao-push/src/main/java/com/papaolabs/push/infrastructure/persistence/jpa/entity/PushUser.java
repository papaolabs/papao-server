package com.papaolabs.push.infrastructure.persistence.jpa.entity;

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
    private Long userId;
    private String deviceId;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "PushUser{" +
            "id=" + id +
            ", userId=" + userId +
            ", deviceId='" + deviceId + '\'' +
            '}';
    }
}
