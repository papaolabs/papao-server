package com.papaolabs.api.infrastructure.persistence.restapi.vision;

import com.papaolabs.api.infrastructure.persistence.restapi.vision.dto.VisionApiRequest;
import com.papaolabs.api.infrastructure.persistence.restapi.vision.dto.VisionApiResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "visionApiClient", fallbackFactory = VisionApiClientFallbackFactory.class)
public interface VisionApiClient {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /images:annotate?key={key}")
    VisionApiResponse image(@Param(value = "key") String key, VisionApiRequest request);
}
