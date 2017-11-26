package com.papaolabs.api.interfaces.v1.controller.request;

public class PushRequest {
    private String type;
    private String userId;
    private String deviceId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushRequest that = (PushRequest) o;
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        return deviceId != null ? deviceId.equals(that.deviceId) : that.deviceId == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushRequest{" +
            "type='" + type + '\'' +
            ", userId='" + userId + '\'' +
            ", deviceId='" + deviceId + '\'' +
            '}';
    }
}
