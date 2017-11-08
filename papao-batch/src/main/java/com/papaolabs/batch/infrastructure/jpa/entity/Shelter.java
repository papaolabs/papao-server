package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "shelter_tb")
public class Shelter extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long sidoCode;
    private String sidoName;
    private Long gunguCode;
    private String gunguName;
    private Long shelterCode;
    private String shelterName;
}
