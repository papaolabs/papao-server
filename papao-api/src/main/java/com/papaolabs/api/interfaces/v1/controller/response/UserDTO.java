package com.papaolabs.api.interfaces.v1.controller.response;

import java.util.List;

public class UserDTO {
    private String userId;
    private String nickname;
    private String phone;
    private String profileUrl;
    private List<String> devicesToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public List<String> getDevicesToken() {
        return devicesToken;
    }

    public void setDevicesToken(List<String> devicesToken) {
        this.devicesToken = devicesToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        if (userId != null ? !userId.equals(userDTO.userId) : userDTO.userId != null) {
            return false;
        }
        if (nickname != null ? !nickname.equals(userDTO.nickname) : userDTO.nickname != null) {
            return false;
        }
        if (phone != null ? !phone.equals(userDTO.phone) : userDTO.phone != null) {
            return false;
        }
        if (profileUrl != null ? !profileUrl.equals(userDTO.profileUrl) : userDTO.profileUrl != null) {
            return false;
        }
        return devicesToken != null ? devicesToken.equals(userDTO.devicesToken) : userDTO.devicesToken == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (profileUrl != null ? profileUrl.hashCode() : 0);
        result = 31 * result + (devicesToken != null ? devicesToken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "userId='" + userId + '\'' +
            ", nickname='" + nickname + '\'' +
            ", phone='" + phone + '\'' +
            ", profileUrl='" + profileUrl + '\'' +
            ", devicesToken=" + devicesToken +
            '}';
    }
}
