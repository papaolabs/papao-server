package com.papaolabs.api.restapi;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.papaolabs.api.restapi.dto.AnimalApiResponse;
import com.papaolabs.api.restapi.dto.AnimalKindApiResponse;
import com.papaolabs.api.restapi.dto.RegionApiResponse;
import com.papaolabs.api.restapi.dto.ShelterApiResponse;
import feign.Param;
import feign.hystrix.FallbackFactory;
import org.springframework.util.MultiValueMap;

public class AnimalApiClientFallbackFactory implements FallbackFactory<AnimalApiClient> {
    private static final AnimalApiClient FALLBACK = new AnimalApiFallback();

    @Override
    public AnimalApiClient create(Throwable cause) {
        if (cause instanceof HystrixRuntimeException) {
            HystrixRuntimeException hystrixRuntimeException = HystrixRuntimeException.class.cast(cause);
            if (HystrixRuntimeException.FailureType.SHORTCIRCUIT == hystrixRuntimeException.getFailureType()) {
                return FALLBACK;
            }
        }
        return FALLBACK;
    }

    public static class AnimalApiFallback implements AnimalApiClient {
        private void logging(MultiValueMap<String, String> queries) {
            // Todo
        }


        @Override
        public RegionApiResponse sido(@Param(value = "serviceKey") String serviceKey) {
            return null;
        }

        @Override
        public RegionApiResponse sigungu(@Param(value = "serviceKey") String serviceKey, @Param(value = "uprCd") String uprCd) {
            return null;
        }

        @Override
        public ShelterApiResponse shelter(@Param(value = "serviceKey") String serviceKey, @Param(value = "uprCd") String uprCd, @Param(value = "orgCd") String orgCd) {
            return null;
        }

        @Override
        public AnimalKindApiResponse kind(@Param(value = "serviceKey") String serviceKey, @Param(value = "upKindCd") String upKindCd) {
            return null;
        }

        @Override
        public AnimalApiResponse animal(@Param(value = "serviceKey") String serviceKey, @Param(value = "bgnde") String bgnde, @Param(value = "endde") String endde, @Param(value = "upkind") String upKind, @Param(value = "kind") String kind, @Param(value = "upr_cd") String uprCd, @Param(value = "org_cd") String orgCd, @Param(value = "care_reg_no") String careRegNo, @Param(value = "state") String state, @Param(value = "pageNo") String pageNo, @Param(value = "numOfRows") String numOfRows) {
            return null;
        }
    }
}
