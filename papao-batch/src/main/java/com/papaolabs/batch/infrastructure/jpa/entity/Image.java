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
@Table(name = "image_tb")
public class Image {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private Long postId;
}
