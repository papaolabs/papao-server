package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shelter_tb")
public class Shelter extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long sidoCode;
    private String sidoName;
    private Long gunguCode;
    private String gunguName;
    @Column(unique = true)
    private Long shelterCode;
    private String shelterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSidoCode() {
        return sidoCode;
    }

    public void setSidoCode(Long sidoCode) {
        this.sidoCode = sidoCode;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public Long getGunguCode() {
        return gunguCode;
    }

    public void setGunguCode(Long gunguCode) {
        this.gunguCode = gunguCode;
    }

    public String getGunguName() {
        return gunguName;
    }

    public void setGunguName(String gunguName) {
        this.gunguName = gunguName;
    }

    public Long getShelterCode() {
        return shelterCode;
    }

    public void setShelterCode(Long shelterCode) {
        this.shelterCode = shelterCode;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }
}
