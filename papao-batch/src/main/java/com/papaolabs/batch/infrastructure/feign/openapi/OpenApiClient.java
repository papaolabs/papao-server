package com.papaolabs.batch.infrastructure.feign.openapi;

import com.papaolabs.batch.infrastructure.feign.openapi.dto.AnimalDTO;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import java.util.List;

@FeignClient(name = "openapi", fallbackFactory = OpenApiClientFallbackFactory.class)
public interface OpenApiClient {
    @RequestLine("GET /openapi/v1/operation/animals?beginDate={beginDate}&endDate={endDate}")
    List<AnimalDTO> animal(@Param("beginDate") String beginDate,
                           @Param("endDate") String endDate);
}
