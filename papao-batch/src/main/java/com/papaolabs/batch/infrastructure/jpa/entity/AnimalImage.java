package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "animal_image_tb")
public class AnimalImage {
    @Id
    @GeneratedValue
    private Long id;
    private Long abandonedAnimalId;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abandonedAnimalId", insertable = false, updatable = false)
    private AbandonedAnimal abandonedAnimal;
}
