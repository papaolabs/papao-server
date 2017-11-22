package com.papaolabs.api.infrastructure.feign.openapi;

import com.papaolabs.api.infrastructure.feign.openapi.dto.PushDTO;
import com.papaolabs.api.infrastructure.feign.openapi.dto.PushTypeDTO;
import com.papaolabs.api.interfaces.v1.controller.response.PushHistoryDTO;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.List;

@FeignClient(name = "pushapi", fallbackFactory = PushApiClientFallbackFactory.class)
public interface PushApiClient {
    @RequestLine("POST /api/v1/push/send?postId={postId}")
    @Headers("Content-Type: application/json")
    @Body("%7B\"type\": \"{type}\", \"userId\": \"{userId}\", \"message\": \"{message}\"%7D")
    List<PushDTO> sendPush(@Param(value = "type") String type,
                           @Param(value = "userId") String userId,
                           @Param(value = "message") String message,
                           @Param(value = "postId") String postId);

    @RequestLine("GET /api/v1/push?userId={userId}&index={index}&size={size}")
    PushHistoryDTO ownPushList(@Param(value = "userId") String userId,
                               @Param(value = "index") String index,
                               @Param(value = "size") String size);

    @RequestLine("POST /api/v1/push/setting")
    @Headers("Content-Type: application/json")
    @Body("%7B\"userId\": \"{userId}\", \"deviceId\": \"{deviceId}\", \"alarmYn\": \"{alarmYn}\", \"rescueAlarmYn\": \"{rescueAlarmYn}\", " +
            "\"postAlarmYn\": \"{postAlarmYn}\"%7D")
    PushTypeDTO setPushType(@Param(value = "userId") String userId,
                            @Param(value = "deviceId") String deviceId,
                            @Param(value = "alarmYn") String alarmYn,
                            @Param(value = "rescueAlarmYn") String rescueAlarmYn,
                            @Param(value = "postAlarmYn") String postAlarmYn);
}
