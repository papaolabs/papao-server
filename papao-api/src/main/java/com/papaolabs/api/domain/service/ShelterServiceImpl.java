package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Shelter;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.ShelterRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.ShelterDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelterServiceImpl implements ShelterService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    @NotNull
    private final AnimalApiClient animalApiClient;
    @NotNull
    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(AnimalApiClient animalApiClient, ShelterRepository shelterRepository) {
        this.animalApiClient = animalApiClient;
        this.shelterRepository = shelterRepository;
    }

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
    public List<ShelterDTO> getCities(Long cityCode) {
        return shelterRepository.findByCityCode(cityCode).stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShelterDTO> getTowns(Long townCode) {
        return shelterRepository.findByTownCode(townCode).stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShelterDTO> getShelters() {
        return shelterRepository.findAll().stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    private ShelterDTO transform(Shelter shelter) {
        ShelterDTO shelterDTO = new ShelterDTO();
        shelterDTO.setCityCode(shelter.getCityCode());
        shelterDTO.setCityName(shelter.getCityName());
        shelterDTO.setTownCode(shelter.getTownCode());
        shelterDTO.setTownName(shelter.getTownName());
        shelterDTO.setShelterCode(shelter.getShelterCode());
        shelterDTO.setShelterName(shelter.getShelterName());
        return shelterDTO;
    }
}
