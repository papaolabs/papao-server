package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "shelter_tb")
public class Shelter extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long code;
    private String name;
    @OneToOne
    private Region region;
}
