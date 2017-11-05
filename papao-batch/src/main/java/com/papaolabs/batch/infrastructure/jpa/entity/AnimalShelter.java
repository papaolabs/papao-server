package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "animal_shelter_tb")
public class AnimalShelter extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long regionId;
    @Column(unique = true)
    private Long code;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId", insertable = false, updatable = false)
    private Region region;
}
