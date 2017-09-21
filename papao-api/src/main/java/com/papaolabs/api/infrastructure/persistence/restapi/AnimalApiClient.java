package com.papaolabs.api.infrastructure.persistence.restapi;

import com.papaolabs.api.infrastructure.persistence.restapi.dto.AnimalApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.AnimalKindApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.RegionApiResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.dto.ShelterApiResponse;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(fallbackFactory = AnimalApiClientFallbackFactory.class)
public interface AnimalApiClient {

    @RequestLine("GET /sido?serviceKey={serviceKey}")
    RegionApiResponse sido(@Param(value = "serviceKey") String serviceKey);

    @RequestLine("GET /sigungu?serviceKey={serviceKey}&upr_cd={uprCd}")
    RegionApiResponse sigungu(@Param(value = "serviceKey") String serviceKey, @Param(value = "uprCd") String uprCd);

    @RequestLine("GET /shelter?serviceKey={serviceKey}&upr_cd={uprCd}&org_cd={orgCd}")
    ShelterApiResponse shelter(@Param(value = "serviceKey") String serviceKey,
                               @Param(value = "uprCd") String uprCd,
                               @Param(value = "orgCd") String orgCd);

    @RequestLine("GET /kind?serviceKey={serviceKey}&up_kind_cd={upKindCd}")
    AnimalKindApiResponse kind(@Param(value = "serviceKey") String serviceKey, @Param(value = "upKindCd") String upKindCd);

    @RequestLine("GET /abandonmentPublic?serviceKey={serviceKey}"
            + "&bgnde={bgnde}&endde={endde}&upkind={upkind}&kind={kind}"
            + "&upr_cd={upr_cd}&org_cd={org_cd}&care_reg_no={care_reg_no}"
            + "&state={state}&pageNo={pageNo}&numOfRows={numOfRows}")
    AnimalApiResponse animal(@Param(value = "serviceKey") String serviceKey,
                             @Param(value = "bgnde") String bgnde,
                             @Param(value = "endde") String endde,
                             @Param(value = "upkind") String upKind,
                             @Param(value = "kind") String kind,
                             @Param(value = "upr_cd") String uprCd,
                             @Param(value = "org_cd") String orgCd,
                             @Param(value = "care_reg_no") String careRegNo,
                             @Param(value = "state") String state,
                             @Param(value = "pageNo") String pageNo,
                             @Param(value = "numOfRows") String numOfRows);
}
