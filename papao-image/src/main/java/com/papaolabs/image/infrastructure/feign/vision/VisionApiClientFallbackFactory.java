package com.papaolabs.image.infrastructure.feign.vision;

import com.papaolabs.image.infrastructure.feign.LoggingFallbackFactory;
import com.papaolabs.image.infrastructure.feign.vision.dto.VisionApiRequest;
import com.papaolabs.image.infrastructure.feign.vision.dto.VisionApiResponse;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Slf4j
public class VisionApiClientFallbackFactory implements LoggingFallbackFactory<VisionApiClient> {
    private static final VisionApiClient FALLBACK = new VisionApiFallback();

    @Override
    public VisionApiClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class VisionApiFallback implements VisionApiClient {
        @Override
        public VisionApiResponse image(@Param(value = "mid") String serviceKey, @RequestBody VisionApiRequest request) {
            log.debug("service mid : {}, request : {}", serviceKey, request.toString());
            return new VisionApiResponse();
        }
    }
}
