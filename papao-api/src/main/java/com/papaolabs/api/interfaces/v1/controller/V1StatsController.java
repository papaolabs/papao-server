package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.StatsService;
import com.papaolabs.api.interfaces.v1.controller.response.StatsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/stats")
public class V1StatsController {
    @NotNull
    private final StatsService statsService;

    public V1StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public StatsDTO stats(@RequestParam(required = false) String beginDate, @RequestParam(required = false) String endDate) {
        return this.statsService.getTotalStats(beginDate, endDate);
    }
}
