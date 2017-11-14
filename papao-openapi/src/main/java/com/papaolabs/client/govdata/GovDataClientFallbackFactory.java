package com.papaolabs.client.govdata;

import com.papaolabs.client.LoggingFallbackFactory;
import com.papaolabs.client.govdata.dto.AnimalResponse;
import com.papaolabs.client.govdata.dto.KindResponse;
import com.papaolabs.client.govdata.dto.RegionResponse;
import com.papaolabs.client.govdata.dto.ShelterResponse;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GovDataClientFallbackFactory implements LoggingFallbackFactory<GovDataClient> {
    private static final GovDataClient FALLBACK = new GovDataFallback();

    @Override
    public GovDataClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class GovDataFallback implements GovDataClient {
        @Override
        public RegionResponse sido(@Param(value = "serviceKey") String serviceKey) {
            log.debug("AnimalApiFallback.sido() : serviceKey {}", serviceKey);
            return new RegionResponse();
        }

        @Override
        public RegionResponse sigungu(@Param(value = "serviceKey") String serviceKey, @Param(value = "uprCd") String uprCd) {
            log.debug("AnimalApiFallback.sido() : serviceKey : {}, uprCd : {}", serviceKey, uprCd);
            return new RegionResponse();
        }

        @Override
        public ShelterResponse shelter(@Param(value = "serviceKey") String serviceKey,
                                       @Param(value = "uprCd") String uprCd,
                                       @Param(value = "orgCd") String orgCd) {
            log.debug("AnimalApiFallback.shelter() : serviceKey {}, uprCd : {}, orgCd : {}", serviceKey, uprCd, orgCd);
            return new ShelterResponse();
        }

        @Override
        public KindResponse kind(@Param(value = "serviceKey") String serviceKey, @Param(value = "upKindCd") String upKindCd) {
            log.debug("AnimalApiFallback.breed() : serviceKey {}, upKindCd : {}", serviceKey, upKindCd);
            return new KindResponse();
        }

        @Override
        public AnimalResponse animal(@Param(value = "serviceKey") String serviceKey,
                                     @Param(value = "bgnde") String bgnde,
                                     @Param(value = "endde") String endde,
                                     @Param(value = "upkind") String upKind,
                                     @Param(value = "breed") String kind,
                                     @Param(value = "upr_cd") String uprCd,
                                     @Param(value = "org_cd") String orgCd,
                                     @Param(value = "care_reg_no") String careRegNo,
                                     @Param(value = "state") String state,
                                     @Param(value = "pageNo") String pageNo,
                                     @Param(value = "numOfRows") String numOfRows) {
            log.debug(
                "AnimalApiFallback.animal() : serviceKey {}, beginDate : {}, endDate : {}, upKindCode : {}, kindCode : {}, uprCode : {}, " +
                    "orgCode : {}, careRegNo : {}, state : {}, pageNo : {}, numOfRows : {}",
                serviceKey, bgnde, endde, upKind, kind, uprCd, orgCd, careRegNo, state, pageNo, numOfRows);
            return new AnimalResponse();
        }
    }
}
