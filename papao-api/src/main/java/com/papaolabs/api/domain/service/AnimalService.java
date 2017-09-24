package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalKindApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.AnimalRequest;
import com.papaolabs.api.interfaces.v1.dto.KindDTO;

import java.util.List;

public interface AnimalService {
    List<KindDTO> getKindList();

    AnimalApiResponse getAnimalList(AnimalRequest animalRequest);
}
