package com.papaolabs.api.infrastructure.feign.openapi;

import com.papaolabs.api.infrastructure.feign.openapi.dto.PushDTO;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.List;

@FeignClient(name = "pushapi", fallbackFactory = PushApiClientFallbackFactory.class)
public interface PushApiClient {
    @RequestLine("GET /api/v1/push")
    List<PushDTO> sample();
}
