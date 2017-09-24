package com.papaolabs.api.domain.service;

import com.papaolabs.api.domain.model.Kind;
import com.papaolabs.api.infrastructure.persistence.jpa.repository.KindRepository;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalApiResponse.Body.Items.AnimalItemDTO;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.AnimalKindApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.feign.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.AnimalRequest;
import com.papaolabs.api.interfaces.v1.dto.FeedDTO;
import com.papaolabs.api.interfaces.v1.dto.KindDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

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
        return kindRepository.findAll().stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedDTO> getAnimalList(String beginDate, String endDate, String upKindCode, String kindCode, String uprCode, String orgCode, String shelterCode, String state, String pageNo, String size) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        if(isEmpty(beginDate)) {
            beginDate = now.format(formatter);
        }
        if(isEmpty(endDate)) {
            endDate = now.format(formatter);
        }
        return animalApiClient.animal(appKey, beginDate, endDate, upKindCode, kindCode,
                uprCode, orgCode, shelterCode, state, pageNo, size)
                .getBody().getItems().getItem().stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    private KindDTO transform(Kind kind) {
        KindDTO kindDTO = new KindDTO();
        kindDTO.setUpKindCode(kind.getUpKindCode());
        kindDTO.setKindCode(kind.getKindCode());
        kindDTO.setKindName(kind.getKindName());
        return kindDTO;
    }

    private FeedDTO transform(AnimalItemDTO animalItemDTO) {
        FeedDTO feedDTO = new FeedDTO();
        feedDTO.setNoticeNo(animalItemDTO.getNoticeNo());
        feedDTO.setNoticeBeginDate(animalItemDTO.getNoticeSdt());
        feedDTO.setNoticeEndDate(animalItemDTO.getNoticeEdt());
        feedDTO.setImageUrl(animalItemDTO.getPopfile());
        feedDTO.setThumbImageUrl(animalItemDTO.getFilename());
        feedDTO.setState(animalItemDTO.getProcessState());
        feedDTO.setGender(animalItemDTO.getSexCd());
        feedDTO.setNeuterYn(animalItemDTO.getNeuterYn());
        feedDTO.setDescription(animalItemDTO.getSpecialMark());
        feedDTO.setShelterName(animalItemDTO.getCareNm());
        feedDTO.setShelterTel(animalItemDTO.getCareTel());
        feedDTO.setShelterAddress(animalItemDTO.getCareAddr());
        feedDTO.setDepartment(animalItemDTO.getOrgNm());
        feedDTO.setManagerName(animalItemDTO.getChargeNm());
        feedDTO.setManagerTel(animalItemDTO.getOfficetel());
        feedDTO.setNote(animalItemDTO.getNoticeComment());
        feedDTO.setDesertionNo(animalItemDTO.getDesertionNo());
        feedDTO.setHappenDate(animalItemDTO.getHappenDt());
        feedDTO.setHappenPlace(animalItemDTO.getHappenPlace());
        feedDTO.setKindCode(animalItemDTO.getKindCd());
        feedDTO.setColorCode(animalItemDTO.getColorCd());
        feedDTO.setAge(animalItemDTO.getAge());
        feedDTO.setWeight(animalItemDTO.getWeight());
        return feedDTO;
    }
}
