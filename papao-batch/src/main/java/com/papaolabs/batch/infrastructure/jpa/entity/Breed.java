package com.papaolabs.batch.infrastructure.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "breed_tb")
public class Breed extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long upKindCode;
    private String upKindName;
    @Column(unique = true)
    private Long kindCode;
    private String kindName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUpKindCode() {
        return upKindCode;
    }

    public void setUpKindCode(Long upKindCode) {
        this.upKindCode = upKindCode;
    }

    public String getUpKindName() {
        return upKindName;
    }

    public void setUpKindName(String upKindName) {
        this.upKindName = upKindName;
    }

    public Long getKindCode() {
        return kindCode;
    }

    public void setKindCode(Long kindCode) {
        this.kindCode = kindCode;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
}
