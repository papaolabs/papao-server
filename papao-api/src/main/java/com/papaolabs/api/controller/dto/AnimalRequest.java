package com.papaolabs.api.controller.dto;

import lombok.Data;

@Data
public class AnimalRequest {
    private String bgnde;
    private String endde;
    private String upkind;
    private String kind;
    private String upr_cd;
    private String org_cd;
    private String careRegNo;
    private String state;
    private String pageNo;
    private String numOfRows;
}
