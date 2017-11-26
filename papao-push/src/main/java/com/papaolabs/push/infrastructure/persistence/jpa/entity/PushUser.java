package com.papaolabs.push.infrastructure.persistence.jpa.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "push_user_tb")
public class PushUser extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserType type;
    private String userId;
    private String deviceId;
    @Enumerated(EnumType.STRING)
    private YesNoType alarmYn;
    @Enumerated(EnumType.STRING)
    private YesNoType rescueAlarmYn;
    @Enumerated(EnumType.STRING)
    private YesNoType postAlarmYn;

    public enum YesNoType {
        Y, N, U;

        public static YesNoType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (YesNoType type : YesNoType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return U;
        }
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public YesNoType getAlarmYn() {
        return alarmYn;
    }

    public void setAlarmYn(YesNoType alarmYn) {
        this.alarmYn = alarmYn;
    }

    public YesNoType getRescueAlarmYn() {
        return rescueAlarmYn;
    }

    public void setRescueAlarmYn(YesNoType rescueAlarmYn) {
        this.rescueAlarmYn = rescueAlarmYn;
    }

    public YesNoType getPostAlarmYn() {
        return postAlarmYn;
    }

    public void setPostAlarmYn(YesNoType postAlarmYn) {
        this.postAlarmYn = postAlarmYn;
    }

    @Override
    public String toString() {
        return "PushUser{" +
            "id=" + id +
            ", type=" + type +
            ", userId='" + userId + '\'' +
            ", deviceId='" + deviceId + '\'' +
            ", alarmYn=" + alarmYn +
            ", rescueAlarmYn=" + rescueAlarmYn +
            ", postAlarmYn=" + postAlarmYn +
            '}';
    }
}
