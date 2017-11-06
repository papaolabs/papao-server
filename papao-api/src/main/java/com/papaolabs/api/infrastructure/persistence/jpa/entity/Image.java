package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "image_tb")
public class Image {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private Long postId;
}
