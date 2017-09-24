package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Shelter;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.ShelterDTO;

import java.util.List;

public interface ShelterService {
    RegionApiResponse getSidoList();

    RegionApiResponse getSigunguList(String uprCd);

    ShelterApiResponse getShelterList(String uprCd, String orgCd);

    ShelterDTO getCities(String cityCode);

    ShelterDTO getTowns(String townCode);

    List<ShelterDTO> getRegions();
}
