package com.papaolabs.api.infrastructure.feign.openapi;

import com.papaolabs.api.infrastructure.feign.openapi.dto.PushDTO;
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
    @Body("%7B\"userId\": \"{userId}\", \"message\": \"{message}\"%7D")
    List<PushDTO> sendPush(@Param(value = "userId") String userId,
                           @Param(value = "message") String message,
                           @Param(value = "postId") String postId);
}
