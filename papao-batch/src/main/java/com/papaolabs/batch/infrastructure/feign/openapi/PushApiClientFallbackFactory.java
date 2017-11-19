package com.papaolabs.batch.infrastructure.feign.openapi;

import com.papaolabs.api.infrastructure.feign.LoggingFallbackFactory;
import com.papaolabs.api.infrastructure.feign.openapi.dto.PushDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PushApiClientFallbackFactory implements LoggingFallbackFactory<PushApiClient> {
    private static final PushApiClient FALLBACK = new OpenApiFallback();

    @Override
    public PushApiClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class OpenApiFallback implements PushApiClient {
        @Override
        public List<PushDTO> sendPush(String userId, String message, String postId) {
            return null;
        }
    }
}
