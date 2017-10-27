package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.AnimalResponse.Body.Items.AnimalItem;
import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.KindResponse.Body.Items.KindItem;
import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.RegionResponse.Body.Items.RegionItem;
import com.papaolabs.openapi.infrastructure.persistence.feign.govdata.dto.ShelterResponse.Body.Items.ShelterItem;

import java.util.List;

public interface GovDataService {
    List<AnimalItem> readAnimalItems(String beginDate,
                                     String endDate,
                                     String categoryCode,
                                     String kindCode,
                                     String sidoCode,
                                     String gunguCode,
                                     String shelterCode,
                                     String state,
                                     String index,
                                     String size);

    List<KindItem> readKindItems(String categoryCode);

    List<RegionItem> readSidoItems();

    List<RegionItem> readGunguItems(String sidoCode);

    List<ShelterItem> readShelterItems(String sidoCode, String gunguCode);
}
