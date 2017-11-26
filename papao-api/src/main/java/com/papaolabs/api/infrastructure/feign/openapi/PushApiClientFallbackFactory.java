package com.papaolabs.api.infrastructure.feign.openapi;

import com.papaolabs.api.infrastructure.feign.LoggingFallbackFactory;
import com.papaolabs.api.infrastructure.feign.openapi.dto.PushDTO;
import com.papaolabs.api.infrastructure.feign.openapi.dto.PushTypeDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushHistoryDTO;
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
        public List<PushDTO> sendPush(String type, String userId, String message, String postId) {
            return null;
        }

        @Override
        public PushHistoryDTO ownPushList(String userId, String index, String size) {
            return null;
        }

        @Override
        public PushTypeDTO setPushType(String userId, String deviceId, String alarmYn, String rescueAlarmYn, String postAlarmYn) {
            return null;
        }
    }
}
