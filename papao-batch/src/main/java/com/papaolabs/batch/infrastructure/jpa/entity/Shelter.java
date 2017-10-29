package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "shelter_info_tb")
public class Shelter {
    @Id
    @GeneratedValue
    private Long id;
    private Long sidoCode;
    private String sidoName;
    private Long gunguCode;
    private String gunguName;
    private Long shelterCode;
    private String shelterName;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;
}
