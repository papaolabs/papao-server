package com.papaolabs.api.service;

import com.papaolabs.api.controller.dto.AnimalRequest;
import com.papaolabs.api.restapi.dto.AnimalApiResponse;
import com.papaolabs.api.restapi.dto.AnimalKindApiResponse;
import com.papaolabs.api.restapi.dto.RegionApiResponse;
import com.papaolabs.api.restapi.dto.ShelterApiResponse;

public interface AnimalService {
    RegionApiResponse getSidoList();

    RegionApiResponse getSigunguList(String uprCd);

    ShelterApiResponse getShelterList(String uprCd, String orgCd);

    AnimalKindApiResponse getKindList(String upKindCd);

    AnimalApiResponse getAnimalList(AnimalRequest animalRequest);
}
