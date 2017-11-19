package com.papaolabs.batch.infrastructure.feign.openapi;

import feign.Feign;
import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static feign.FeignException.errorStatus;

@Configuration
@EnableAutoConfiguration
@EnableCircuitBreaker
@ComponentScan
public class PushApiConfig {
    @Value("${pushapi.url}")
    private String pushApiUrl;

    @Bean
    public PushApiClient pushApiClient() {
        return Feign.builder()
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logLevel(Logger.Level.BASIC)
                    .target(PushApiClient.class, pushApiUrl);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            private ErrorDecoder defaultDecoder = new Default();

            @Override
            public Exception decode(String methodKey, Response response) {
                int status = response.status();
                if (400 <= status && status <= 500) {
                    FeignException feignException = errorStatus(methodKey, response);
                    return new Exception("feign decode error : " + status);
                }
                return defaultDecoder.decode(methodKey, response);
            }
        };
    }
}
