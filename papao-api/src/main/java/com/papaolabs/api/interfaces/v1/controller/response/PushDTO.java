package com.papaolabs.api.interfaces.v1.controller.response;

import java.util.List;

public class PushDTO {
    private String userId;
    private List<String> deviceIds;

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
        if (userId != null ? !userId.equals(pushDTO.userId) : pushDTO.userId != null) {
            return false;
        }
        return deviceIds != null ? deviceIds.equals(pushDTO.deviceIds) : pushDTO.deviceIds == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (deviceIds != null ? deviceIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushDTO{" +
            "userId='" + userId + '\'' +
            ", deviceIds=" + deviceIds +
            '}';
    }
}
