package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.restapi.AnimalApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.AnimalApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.AnimalKindApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.ShelterApiResponse;
import com.papaolabs.api.interfaces.v1.dto.AnimalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

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
        if ("-1".equals(upKindCd)) {
            upKindCd = "";
        }
        return animalApiClient.kind(appKey, upKindCd);
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

    // Todo 임시로 데이터 INSERT 구문 만들기위한 메소드임
    @Override
    public void printRegionSql() {
        Map<String, String> map = new HashMap<>();
        RegionApiResponse response = animalApiClient.sido(appKey);
        response.getBody()
                .getItems()
                .getItem()
                .forEach(x -> map.put(x.getOrgCd(), x.getOrgdownNm()));
        response.getBody()
                .getItems()
                .getItem()
                .stream()
                .map(x -> {
                    return animalApiClient.sigungu(appKey, x.getOrgCd());
                })
                .map(RegionApiResponse::getBody)
                .map(RegionApiResponse.Body::getItems)
                .map(RegionApiResponse.Body.Items::getItem)
                .forEach(x -> x.forEach(y -> System.out.println(
                    "INSERT INTO REGION_INFO_TB (CITY_CODE, CITY_NAME, TOWN_CODE, TOWN_NAME) VALUES (" + y.getUprCd() + ", " + map.get(y.getUprCd()) + ", " + y.getOrgCd() + ", " + y.getOrgdownNm() + ");")));
    }
}
