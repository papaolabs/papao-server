package com.papaolabs.api.domain.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
@Table(name = "KIND_INFO_TB")
public class Kind {
    @Id
    @GeneratedValue
    private Long id;
    private Long upKindCode;
    private Long kindCode;
    private String kindName;
    @Column(name = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    @LastModifiedDate
    private Date lastModifiedDate;
}
