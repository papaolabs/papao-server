package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.restapi.dto.AnimalApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.AnimalKindApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.AnimalRequest;

public interface AnimalService {
    RegionApiResponse getSidoList();

    RegionApiResponse getSigunguList(String uprCd);

    ShelterApiResponse getShelterList(String uprCd, String orgCd);

    AnimalKindApiResponse getKindList(String upKindCd);

    AnimalApiResponse getAnimalList(AnimalRequest animalRequest);
}
