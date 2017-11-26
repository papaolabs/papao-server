package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_tb")
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String uid;
    private String profileUrl;
    private String nickName;
    private String phone;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
        if (uid != null ? !uid.equals(user.uid) : user.uid != null) {
            return false;
        }
        if (profileUrl != null ? !profileUrl.equals(user.profileUrl) : user.profileUrl != null) {
            return false;
        }
        if (nickName != null ? !nickName.equals(user.nickName) : user.nickName != null) {
            return false;
        }
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) {
            return false;
        }
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (profileUrl != null ? profileUrl.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", uid='" + uid + '\'' +
            ", profileUrl='" + profileUrl + '\'' +
            ", nickName='" + nickName + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
