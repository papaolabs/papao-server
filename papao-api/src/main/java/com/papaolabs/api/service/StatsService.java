package com.papaolabs.api.service;

import com.papaolabs.api.controller.dto.StatsDTO;

public interface StatsService {

    StatsDTO getStats(String beginDate, String endDate);
}
