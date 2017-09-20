package com.papaolabs.api.controller;

import com.papaolabs.api.controller.dto.AnimalRequest;
import com.papaolabs.api.controller.dto.StatsDTO;
import com.papaolabs.api.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/dashboard")
@RequiredArgsConstructor
public class V1DashboardController {

    @NotNull
    private final StatsService statsService;

    @GetMapping("/stats")
    public StatsDTO intro(AnimalRequest animalRequest) {
        return statsService.getStats(animalRequest.getBgnde(), animalRequest.getEndde());
    }
}
