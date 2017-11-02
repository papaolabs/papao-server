package com.papaolabs.api.infrastructure.persistence.jpa.entity;

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
@Table(name = "breed_info_tb")
public class Breed {
    @Id
    @GeneratedValue
    private Long id;
    private Long speciesCode;
    private Long kindCode;
    private String kindName;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;
}
