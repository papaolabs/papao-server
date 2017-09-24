package com.papaolabs.api.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "KIND_INFO_TB")
public class Kind {
    @Id
    @GeneratedValue
    private Long id;
    private Long upKindCode;
    private Long kindCode;
    private String kindName;
}
