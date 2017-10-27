package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.AnimalResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.KindResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.RegionResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.ShelterResponse;

import java.util.List;

public interface GovDataService {
    List<AnimalResponse.Body.Items.AnimalItem> readAnimalItems(String beginDate,
                                                               String endDate,
                                                               String categoryCode,
                                                               String kindCode,
                                                               String sidoCode,
                                                               String gunguCode,
                                                               String shelterCode,
                                                               String state,
                                                               String index,
                                                               String size);

    List<KindResponse.Body.Items.KindItem> readKindItems(String categoryCode);

    List<RegionResponse.Body.Items.RegionItem> readSidoItems();

    List<RegionResponse.Body.Items.RegionItem> readGunguItems(String sidoCode);

    List<ShelterResponse.Body.Items.ShelterItem> readShelterItems(String sidoCode, String gunguCode);
}
