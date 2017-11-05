package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "animal_helper_tb")
public class AnimalHelper extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String contact;
    @OneToOne(fetch = FetchType.LAZY)
    private AnimalPost post;
}
