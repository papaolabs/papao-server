package com.papaolabs.api.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "SHELTER_INFO_TB")
public class Shelter {

    @Id
    @GeneratedValue
    private Long id;
    private String cityCode;
    private String cityName;
    private String townCode;
    private String townName;
    private String shelterCode;
    private String shelterName;
}
