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
@EntityListeners(value = { AuditingEntityListener.class })
@Table(name = "SHELTER_INFO_TB")
public class Shelter {

    @Id
    @GeneratedValue
    private Long id;
    private Long cityCode;
    private String cityName;
    private Long townCode;
    private String townName;
    private Long shelterCode;
    private String shelterName;
    @Column(name = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    @LastModifiedDate
    private Date updatedDate;
}
