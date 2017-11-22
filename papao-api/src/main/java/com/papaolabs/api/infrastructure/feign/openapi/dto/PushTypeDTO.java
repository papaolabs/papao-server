package com.papaolabs.api.infrastructure.feign.openapi.dto;

public class PushTypeDTO {
    private String userId;
    private String deviceId;
    private String alarmYn;
    private String rescueAlarmYn;
    private String postAlarmYn;

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

    public String getAlarmYn() {
        return alarmYn;
    }

    public void setAlarmYn(String alarmYn) {
        this.alarmYn = alarmYn;
    }

    public String getRescueAlarmYn() {
        return rescueAlarmYn;
    }

    public void setRescueAlarmYn(String rescueAlarmYn) {
        this.rescueAlarmYn = rescueAlarmYn;
    }

    public String getPostAlarmYn() {
        return postAlarmYn;
    }

    public void setPostAlarmYn(String postAlarmYn) {
        this.postAlarmYn = postAlarmYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushTypeDTO that = (PushTypeDTO) o;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) {
            return false;
        }
        if (alarmYn != null ? !alarmYn.equals(that.alarmYn) : that.alarmYn != null) {
            return false;
        }
        if (rescueAlarmYn != null ? !rescueAlarmYn.equals(that.rescueAlarmYn) : that.rescueAlarmYn != null) {
            return false;
        }
        return postAlarmYn != null ? postAlarmYn.equals(that.postAlarmYn) : that.postAlarmYn == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (alarmYn != null ? alarmYn.hashCode() : 0);
        result = 31 * result + (rescueAlarmYn != null ? rescueAlarmYn.hashCode() : 0);
        result = 31 * result + (postAlarmYn != null ? postAlarmYn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushTypeDTO{" +
            "userId='" + userId + '\'' +
            ", deviceId='" + deviceId + '\'' +
            ", alarmYn='" + alarmYn + '\'' +
            ", rescueAlarmYn='" + rescueAlarmYn + '\'' +
            ", postAlarmYn='" + postAlarmYn + '\'' +
            '}';
    }
}
