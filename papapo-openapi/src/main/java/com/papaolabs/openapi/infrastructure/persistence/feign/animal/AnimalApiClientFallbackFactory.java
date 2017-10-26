package com.papaolabs.openapi.infrastructure.persistence.feign.animal;

import com.papaolabs.openapi.infrastructure.persistence.feign.LoggingFallbackFactory;
import com.papaolabs.openapi.infrastructure.persistence.feign.animal.dto.AnimalApiResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.animal.dto.KindApiResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.animal.dto.RegionApiResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.animal.dto.ShelterApiResponse;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnimalApiClientFallbackFactory implements LoggingFallbackFactory<AnimalApiClient> {
    private static final AnimalApiClient FALLBACK = new AnimalApiFallback();

    @Override
    public AnimalApiClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class AnimalApiFallback implements AnimalApiClient {
        @Override
        public RegionApiResponse sido(@Param(value = "serviceKey") String serviceKey) {
            log.debug("AnimalApiFallback.sido() : serviceKey {}", serviceKey);
            return new RegionApiResponse();
        }

        @Override
        public RegionApiResponse sigungu(@Param(value = "serviceKey") String serviceKey, @Param(value = "uprCd") String uprCd) {
            log.debug("AnimalApiFallback.sido() : serviceKey : {}, uprCd : {}", serviceKey, uprCd);
            return new RegionApiResponse();
        }

        @Override
        public ShelterApiResponse shelter(@Param(value = "serviceKey") String serviceKey,
                                          @Param(value = "uprCd") String uprCd,
                                          @Param(value = "orgCd") String orgCd) {
            log.debug("AnimalApiFallback.shelter() : serviceKey {}, uprCd : {}, orgCd : {}", serviceKey, uprCd, orgCd);
            return new ShelterApiResponse();
        }

        @Override
        public KindApiResponse kind(@Param(value = "serviceKey") String serviceKey, @Param(value = "upKindCd") String upKindCd) {
            log.debug("AnimalApiFallback.kind() : serviceKey {}, upKindCd : {}", serviceKey, upKindCd);
            return new KindApiResponse();
        }

        @Override
        public AnimalApiResponse animal(@Param(value = "serviceKey") String serviceKey,
                                        @Param(value = "bgnde") String bgnde,
                                        @Param(value = "endde") String endde,
                                        @Param(value = "upkind") String upKind,
                                        @Param(value = "kind") String kind,
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
            return new AnimalApiResponse();
        }
    }
}
