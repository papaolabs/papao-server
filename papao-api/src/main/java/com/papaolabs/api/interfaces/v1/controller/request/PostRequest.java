package com.papaolabs.api.interfaces.v1.controller.request;

import java.util.List;

public class PostRequest {
    private String happenDate;
    private String happenPlace;
    private String uid;
    private String postType;
    private String stateType;
    private List<String> imageUrls;
    private Long upKindCode;
    private Long kindCode;
    private String contact;
    private String genderType;
    private String neuterType;
    private Integer age;
    private Float weight;
    private String feature;
    private Long sidoCode;
    private Long gunguCode;

    public String getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(String happenDate) {
        this.happenDate = happenDate;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Long getUpKindCode() {
        return upKindCode;
    }

    public void setUpKindCode(Long upKindCode) {
        this.upKindCode = upKindCode;
    }

    public Long getKindCode() {
        return kindCode;
    }

    public void setKindCode(Long kindCode) {
        this.kindCode = kindCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getNeuterType() {
        return neuterType;
    }

    public void setNeuterType(String neuterType) {
        this.neuterType = neuterType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Long getSidoCode() {
        return sidoCode;
    }

    public void setSidoCode(Long sidoCode) {
        this.sidoCode = sidoCode;
    }

    public Long getGunguCode() {
        return gunguCode;
    }

    public void setGunguCode(Long gunguCode) {
        this.gunguCode = gunguCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostRequest that = (PostRequest) o;
        if (happenDate != null ? !happenDate.equals(that.happenDate) : that.happenDate != null) {
            return false;
        }
        if (happenPlace != null ? !happenPlace.equals(that.happenPlace) : that.happenPlace != null) {
            return false;
        }
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) {
            return false;
        }
        if (postType != null ? !postType.equals(that.postType) : that.postType != null) {
            return false;
        }
        if (stateType != null ? !stateType.equals(that.stateType) : that.stateType != null) {
            return false;
        }
        if (imageUrls != null ? !imageUrls.equals(that.imageUrls) : that.imageUrls != null) {
            return false;
        }
        if (upKindCode != null ? !upKindCode.equals(that.upKindCode) : that.upKindCode != null) {
            return false;
        }
        if (kindCode != null ? !kindCode.equals(that.kindCode) : that.kindCode != null) {
            return false;
        }
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) {
            return false;
        }
        if (genderType != null ? !genderType.equals(that.genderType) : that.genderType != null) {
            return false;
        }
        if (neuterType != null ? !neuterType.equals(that.neuterType) : that.neuterType != null) {
            return false;
        }
        if (age != null ? !age.equals(that.age) : that.age != null) {
            return false;
        }
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) {
            return false;
        }
        if (feature != null ? !feature.equals(that.feature) : that.feature != null) {
            return false;
        }
        if (sidoCode != null ? !sidoCode.equals(that.sidoCode) : that.sidoCode != null) {
            return false;
        }
        return gunguCode != null ? gunguCode.equals(that.gunguCode) : that.gunguCode == null;
    }

    @Override
    public int hashCode() {
        int result = happenDate != null ? happenDate.hashCode() : 0;
        result = 31 * result + (happenPlace != null ? happenPlace.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (postType != null ? postType.hashCode() : 0);
        result = 31 * result + (stateType != null ? stateType.hashCode() : 0);
        result = 31 * result + (imageUrls != null ? imageUrls.hashCode() : 0);
        result = 31 * result + (upKindCode != null ? upKindCode.hashCode() : 0);
        result = 31 * result + (kindCode != null ? kindCode.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (genderType != null ? genderType.hashCode() : 0);
        result = 31 * result + (neuterType != null ? neuterType.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (feature != null ? feature.hashCode() : 0);
        result = 31 * result + (sidoCode != null ? sidoCode.hashCode() : 0);
        result = 31 * result + (gunguCode != null ? gunguCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
            "happenDate='" + happenDate + '\'' +
            ", happenPlace='" + happenPlace + '\'' +
            ", uid='" + uid + '\'' +
            ", postType='" + postType + '\'' +
            ", stateType='" + stateType + '\'' +
            ", imageUrls=" + imageUrls +
            ", upKindCode=" + upKindCode +
            ", kindCode=" + kindCode +
            ", contact='" + contact + '\'' +
            ", genderType='" + genderType + '\'' +
            ", neuterType='" + neuterType + '\'' +
            ", age=" + age +
            ", weight=" + weight +
            ", feature='" + feature + '\'' +
            ", sidoCode=" + sidoCode +
            ", gunguCode=" + gunguCode +
            '}';
    }
}
