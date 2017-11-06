package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
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
}
