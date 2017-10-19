package com.papaolabs.api.domain.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@EntityListeners(value = {AuditingEntityListener.class })
@Table(name = "VISION_COLOR_TB")
public class VisionColor {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "POST_ID")
    private Long postId;
    private Integer red;
    private Integer green;
    private Integer blue;
    private Double score;
    @Column(name = "PIXEL_FRACTION")
    private Double pixelFraction;
    @Column(name = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    @LastModifiedDate
    private Date updatedDate;
}
