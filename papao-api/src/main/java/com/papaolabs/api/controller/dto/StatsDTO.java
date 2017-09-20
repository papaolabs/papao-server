package com.papaolabs.api.controller.dto;

import lombok.Data;

@Data
public class StatsDTO {
    // 입양률
    private Double adoptionRate;
    // 안락사율
    private Double euthanasiaRate;
    // 구조된 동물수
    private Long saveCount;
    // 주인을 만난수
    private Long returnCount;
}
