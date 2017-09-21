package com.papaolabs.api.domain.service;

import com.papaolabs.api.interfaces.v1.dto.StatsDTO;

public interface StatsService {

    StatsDTO getStats(String beginDate, String endDate);
}
