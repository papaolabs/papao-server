package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.restapi.seoul.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.seoul.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.ShelterDTO;

import java.util.List;

public interface ShelterService {
    RegionApiResponse getSidoList();

    RegionApiResponse getSigunguList(String uprCd);

    ShelterApiResponse getShelterList(String uprCd, String orgCd);

    List<ShelterDTO> getCities(Long cityCode);

    List<ShelterDTO> getTowns(Long townCode);

    List<ShelterDTO> getShelters();
}
