package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Kind;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.KindRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalKindApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.AnimalRequest;
import com.papaolabs.api.interfaces.v1.dto.KindDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Value("${seoul.api.animal.appKey}")
    private String appKey;
    @NotNull
    private final AnimalApiClient animalApiClient;
    @NotNull
    private final KindRepository kindRepository;

    public AnimalServiceImpl(AnimalApiClient animalApiClient, KindRepository kindRepository) {
        this.animalApiClient = animalApiClient;
        this.kindRepository = kindRepository;
    }

    @Override
    public List<KindDTO> getKindList() {
        return kindRepository.findAll().stream().map(x -> {
            KindDTO kindDTO = new KindDTO();
            kindDTO.setUpKindCode(x.getUpKindCode());
            kindDTO.setKindCode(x.getKindCode());
            kindDTO.setKindName(x.getKindName());
            return kindDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public AnimalApiResponse getAnimalList(AnimalRequest animalRequest) {
        return animalApiClient.animal(appKey,
                animalRequest.getBgnde(),
                animalRequest.getEndde(),
                animalRequest.getUpkind(),
                animalRequest.getKind(),
                animalRequest.getUpr_cd(),
                animalRequest.getOrg_cd(),
                animalRequest.getCareRegNo(),
                animalRequest.getState(),
                animalRequest.getPageNo(),
                animalRequest.getNumOfRows());
    }
}
