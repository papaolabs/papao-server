package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "region_tb")
public class Region extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long sidoCode;
    private String sidoName;
    @Column(unique = true)
    private Long gunguCode;
    private String gunguName;

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
}
