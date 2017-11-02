package com.papaolabs.batch.infrastructure.feign.openapi;

import com.papaolabs.batch.infrastructure.feign.LoggingFallbackFactory;
import com.papaolabs.batch.infrastructure.feign.openapi.dto.AnimalDTO;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class OpenApiClientFallbackFactory implements LoggingFallbackFactory<OpenApiClient> {
    private static final OpenApiClient FALLBACK = new OpenApiFallback();

    @Override
    public OpenApiClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class OpenApiFallback implements OpenApiClient {
        @Override
        public List<AnimalDTO> animal(String beginDate, String endDate) {
            log.debug("OpenApiClientFallbackFactory.animal() : beginDate {}, endDate : {}", beginDate, endDate);
            return Arrays.asList();
        }
    }
}
