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
    private Long cityCode;
    private String cityName;
    private Long townCode;
    private String townName;
    private Long shelterCode;
    private String shelterName;
}
