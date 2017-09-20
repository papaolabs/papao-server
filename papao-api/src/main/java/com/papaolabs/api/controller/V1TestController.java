package com.papaolabs.api.controller;

import com.papaolabs.api.controller.dto.AnimalRequest;
import com.papaolabs.api.restapi.dto.AnimalApiResponse;
import com.papaolabs.api.restapi.dto.AnimalKindApiResponse;
import com.papaolabs.api.restapi.dto.RegionApiResponse;
import com.papaolabs.api.restapi.dto.ShelterApiResponse;
import com.papaolabs.api.service.AnimalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class V1TestController {
    @NotNull
    private final AnimalServiceImpl animalService;

    @GetMapping("/locations/city")
    public RegionApiResponse sido() {
        return animalService.getSidoList();
    }

    @GetMapping("/locations/city/{cityCode}")
    public RegionApiResponse sigungu(@PathVariable String cityCode) {
        return animalService.getSigunguList(cityCode);
    }

    @GetMapping("/shelters/city/{cityCode}/district/{districtCode}")
    public ShelterApiResponse shelter(@PathVariable String cityCode, @PathVariable String districtCode) {
        return animalService.getShelterList(cityCode, districtCode);
    }

    @GetMapping("/animals/kind/{kindCode}")
    public AnimalKindApiResponse kind(@PathVariable String kindCode) {
        return animalService.getKindList(kindCode);
    }

    @GetMapping("/animals")
    public AnimalApiResponse animal(AnimalRequest animalRequest) {
        return animalService.getAnimalList(animalRequest);
    }
}
