package com.papaolabs.api.interfaces.v1.controller.response;

public class JoinDTO {
    private String id;
    private String phone;
    private String nickName;
    private Boolean isPush;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getPush() {
        return isPush;
    }

    public void setPush(Boolean push) {
        isPush = push;
    }

    @Override
    public String toString() {
        return "JoinDTO{" +
            "id='" + id + '\'' +
            ", phone='" + phone + '\'' +
            ", nickName='" + nickName + '\'' +
            ", isPush=" + isPush +
            '}';
    }
}
