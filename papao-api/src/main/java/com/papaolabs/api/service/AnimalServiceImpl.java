package com.papaolabs.api.service;

import com.papaolabs.api.controller.dto.AnimalRequest;
import com.papaolabs.api.restapi.AnimalApiClient;
import com.papaolabs.api.restapi.dto.AnimalApiResponse;
import com.papaolabs.api.restapi.dto.AnimalKindApiResponse;
import com.papaolabs.api.restapi.dto.RegionApiResponse;
import com.papaolabs.api.restapi.dto.ShelterApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;

    @NotNull
    private final AnimalApiClient animalApiClient;

    @Override
    public RegionApiResponse getSidoList() {
        return animalApiClient.sido(appKey);
    }

    @Override
    public RegionApiResponse getSigunguList(String uprCd) {
        return animalApiClient.sigungu(appKey, uprCd);
    }

    @Override
    public ShelterApiResponse getShelterList(String uprCd, String orgCd) {
        return animalApiClient.shelter(appKey, uprCd, orgCd);
    }

    @Override
    public AnimalKindApiResponse getKindList(String upKindCd) {
        if("-1".equals(upKindCd)) {
            upKindCd = "";
        }
        return animalApiClient.kind(appKey, upKindCd);
    }

    @Override
    public AnimalApiResponse getAnimalList(AnimalRequest animalRequest) {
        return animalApiClient.animal(appKey, animalRequest.getBgnde(), animalRequest.getEndde(), animalRequest.getUpkind(),
                animalRequest.getKind(), animalRequest.getUpr_cd(), animalRequest.getOrg_cd(), animalRequest.getCareRegNo(),
                animalRequest.getState(), animalRequest.getPageNo(), animalRequest.getNumOfRows());
    }
}
