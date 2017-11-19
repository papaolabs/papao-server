package com.papaolabs.api.interfaces.v1.controller.response;

import java.util.List;

public class PushDTO {
    private String type;
    private String userId;
    private List<String> deviceIds;

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

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushDTO pushDTO = (PushDTO) o;
        if (type != null ? !type.equals(pushDTO.type) : pushDTO.type != null) {
            return false;
        }
        if (userId != null ? !userId.equals(pushDTO.userId) : pushDTO.userId != null) {
            return false;
        }
        return deviceIds != null ? deviceIds.equals(pushDTO.deviceIds) : pushDTO.deviceIds == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (deviceIds != null ? deviceIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushDTO{" +
            "type='" + type + '\'' +
            ", userId='" + userId + '\'' +
            ", deviceIds=" + deviceIds +
            '}';
    }
}
