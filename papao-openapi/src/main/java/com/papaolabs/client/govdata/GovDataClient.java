package com.papaolabs.client.govdata;

import com.papaolabs.client.govdata.dto.AnimalResponse;
import com.papaolabs.client.govdata.dto.KindResponse;
import com.papaolabs.client.govdata.dto.RegionResponse;
import com.papaolabs.client.govdata.dto.ShelterResponse;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "govdata", fallbackFactory = GovDataClientFallbackFactory.class)
public interface GovDataClient {

    @RequestLine("GET /sido?serviceKey={serviceKey}")
    RegionResponse sido(@Param(value = "serviceKey") String serviceKey);

    @RequestLine("GET /sigungu?serviceKey={serviceKey}&upr_cd={uprCd}")
    RegionResponse sigungu(@Param(value = "serviceKey") String serviceKey, @Param(value = "uprCd") String uprCd);

    @RequestLine("GET /shelter?serviceKey={serviceKey}&upr_cd={uprCd}&org_cd={orgCd}")
    ShelterResponse shelter(@Param(value = "serviceKey") String serviceKey,
                            @Param(value = "uprCd") String uprCd,
                            @Param(value = "orgCd") String orgCd);

    @RequestLine("GET /breed?serviceKey={serviceKey}&up_kind_cd={upKindCd}")
    KindResponse kind(@Param(value = "serviceKey") String serviceKey, @Param(value = "upKindCd") String upKindCd);

    @RequestLine("GET /abandonmentPublic?serviceKey={serviceKey}"
            + "&bgnde={bgnde}&endde={endde}&upkind={upkind}&breed={breed}"
            + "&upr_cd={upr_cd}&org_cd={org_cd}&care_reg_no={care_reg_no}"
            + "&state={state}&pageNo={pageNo}&numOfRows={numOfRows}")
    AnimalResponse animal(@Param(value = "serviceKey") String serviceKey,
                          @Param(value = "bgnde") String bgnde,
                          @Param(value = "endde") String endde,
                          @Param(value = "upkind") String upKind,
                          @Param(value = "breed") String kind,
                          @Param(value = "upr_cd") String uprCd,
                          @Param(value = "org_cd") String orgCd,
                          @Param(value = "care_reg_no") String careRegNo,
                          @Param(value = "state") String state,
                          @Param(value = "pageNo") String pageNo,
                          @Param(value = "numOfRows") String numOfRows);
}
