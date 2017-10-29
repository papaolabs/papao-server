package com.papaolabs.openapi.domain.service;

import com.papaolabs.client.govdata.GovDataClient;
import com.papaolabs.client.govdata.dto.AnimalResponse.Body.Items.AnimalItem;
import com.papaolabs.client.govdata.dto.KindResponse.Body.Items.KindItem;
import com.papaolabs.client.govdata.dto.RegionResponse.Body.Items.RegionItem;
import com.papaolabs.client.govdata.dto.ShelterResponse.Body.Items.ShelterItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class GovDataServiceImpl implements GovDataService {
    @NotNull
    private final GovDataClient client;
    @Value("${seoul.api.animal.appKey}")
    private String serviceKey;

    public GovDataServiceImpl(GovDataClient client) {
        this.client = client;
    }

    @Override
    public List<AnimalItem> readAnimalItems(String beginDate,
                                            String endDate,
                                            String speciesCode,
                                            String kindCode,
                                            String sidoCode,
                                            String gunguCode,
                                            String shelterCode,
                                            String state,
                                            String index,
                                            String size) {
        beginDate = isEmpty(beginDate) ? getDefaultDate("yyyyMMdd") : beginDate;
        endDate = isEmpty(endDate) ? getDefaultDate("yyyyMMdd") : endDate;
        return client.animal(serviceKey, beginDate, endDate, speciesCode, kindCode, sidoCode, gunguCode, shelterCode, state, index, size)
                     .getBody()
                     .getItems()
                     .getItem();
    }

    @Override
    public List<KindItem> readKindItems(String speciesCode) {
        return isEmpty(speciesCode) ?
            Arrays.asList("417000", "422400", "429900")
                  .stream()
                  .map(this::kind)
                  .flatMap(x -> x.stream())
                  .collect(Collectors.toList())
            : kind(speciesCode);
    }

    @Override
    public List<RegionItem> readSidoItems() {
        return sido();
    }

    @Override
    public List<RegionItem> readGunguItems(String sidoCode) {
        return isEmpty(sidoCode) ? this.readSidoItems()
                                       .stream()
                                       .map(RegionItem::getOrgCd)
                                       .map(this::gungu)
                                       .filter(Objects::nonNull)
                                       .flatMap(Collection::stream)
                                       .collect(Collectors.toList()) : gungu(sidoCode);
    }

    @Override
    public List<ShelterItem> readShelterItems(String sidoCode, String gunguCode) {
        return this.readGunguItems(sidoCode)
                   .stream()
                   .filter(x -> isNotEmpty(gunguCode) ? gunguCode.equals(x.getOrgCd()) : TRUE)
                   .map(x -> shelter(x.getUprCd(), x.getOrgCd()))
                   .filter(Objects::nonNull)
                   .flatMap(Collection::stream)
                   .collect(Collectors.toList());
    }

    private List<KindItem> kind(String speciesCode) {
        return client.kind(serviceKey, speciesCode)
                     .getBody()
                     .getItems()
                     .getItem();
    }

    private List<RegionItem> sido() {
        return client.sido(serviceKey)
                     .getBody()
                     .getItems()
                     .getItem();
    }

    private List<RegionItem> gungu(String sidoCode) {
        return isEmpty(sidoCode) ? Arrays.asList() : client.sigungu(serviceKey, sidoCode)
                                                           .getBody()
                                                           .getItems()
                                                           .getItem();
    }

    private List<ShelterItem> shelter(String sidoCode, String gunguCode) {
        return client.shelter(serviceKey, sidoCode, gunguCode)
                     .getBody()
                     .getItems()
                     .getItem();
    }

    private String getDefaultDate(String format) {
        LocalDateTime now = LocalDateTime.now()
                                         .minusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }
}
