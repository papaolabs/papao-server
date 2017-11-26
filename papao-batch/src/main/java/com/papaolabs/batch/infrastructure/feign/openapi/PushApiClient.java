package com.papaolabs.batch.infrastructure.feign.openapi;

import com.papaolabs.batch.infrastructure.feign.openapi.dto.PushDTO;
import com.papaolabs.batch.infrastructure.feign.openapi.dto.PushHistoryDTO;
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

    @RequestLine("GET /api/v1/push?userId={userId}")
    PushHistoryDTO ownPushList(@Param(value = "userId") String userId);
}
