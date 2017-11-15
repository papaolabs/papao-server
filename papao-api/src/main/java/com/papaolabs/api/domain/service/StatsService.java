package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.controller.response.StatsDTO;

public interface StatsService {
    StatsDTO getTotalStats(String beginDate, String endDate);
}
