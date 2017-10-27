package com.papaolabs.openapi.infrastructure.persistence.feign.vision;

import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiRequest;
import com.papaolabs.openapi.infrastructure.persistence.feign.vision.dto.VisionApiResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "vision", fallbackFactory = VisionApiClientFallbackFactory.class)
public interface VisionApiClient {
    @Headers("Content-Type: application/json")
    @RequestLine("POST /images:annotate?key={key}")
    VisionApiResponse image(@Param(value = "key") String key, VisionApiRequest request);
}
