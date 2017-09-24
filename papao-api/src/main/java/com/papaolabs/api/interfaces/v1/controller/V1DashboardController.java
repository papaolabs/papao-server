package com.papaolabs.api.interfaces.v1.controller;

import com.papaolabs.api.domain.service.AnimalService;
import com.papaolabs.api.domain.service.ShelterService;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.interfaces.v1.dto.*;
import com.papaolabs.api.domain.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/dashboard")
@RequiredArgsConstructor
public class V1DashboardController {

    @NotNull
    private final AnimalService animalService;
    @NotNull
    private final ShelterService shelterService;
    @NotNull
    private final StatsService statsService;

    @GetMapping("/stats")
    public StatsDTO intro(@PathVariable(required = false) String beginDate,
                          @PathVariable(required = false) String endDate) {
        return statsService.getStats(beginDate, endDate);
    }

    @GetMapping("/shelters")
    public List<ShelterDTO> shelters() {
        return shelterService.getShelters();
    }

    @GetMapping("/cities/{cityCode}/shelters")
    public List<ShelterDTO> sheltersByCityCode(@PathVariable Long cityCode) {
        return shelterService.getCities(cityCode);
    }

    @GetMapping("/towns/{townCode}/shelters")
    public List<ShelterDTO> sheltersByTownCode(@PathVariable Long townCode) {
        return shelterService.getTowns(townCode);
    }

    @GetMapping("/kinds")
    public List<KindDTO> kinds() {
        return animalService.getKindList();
    }

    @GetMapping("/animals")
    public List<FeedDTO> animal(@RequestParam(required = false) String beginDate,
                                @RequestParam(required = false) String endDate,
                                @RequestParam(required = false) String upKindCode,
                                @RequestParam(required = false) String kindCode,
                                @RequestParam(required = false) String uprCode,
                                @RequestParam(required = false) String orgCode,
                                @RequestParam(required = false) String shelterCode,
                                @RequestParam(required = false) String state,
                                @RequestParam(defaultValue = "1") String pageNo,
                                @RequestParam(defaultValue = "100000") String numOfRows
                                    ) {
        return animalService.getAnimalList(beginDate, endDate, upKindCode, kindCode, uprCode, orgCode,
        shelterCode, state, pageNo, numOfRows);
    }
}
