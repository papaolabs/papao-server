package com.papaolabs.api.infrastructure.persistence.restapi.feign;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableAutoConfiguration
@EnableFeignClients
@EnableCircuitBreaker
@ComponentScan
public class FeignConfiguration {
    @Value("${httpClient.maxConnTotal:300}")
    private Integer maxConnTotal = 300;
    @Value("${httpClient.maxConnPerRoute:50}")
    private Integer maxConnPerRoute = 50;
    @Value("${feign.logger.level:BASIC}")
    private Logger.Level feignLoggerLevel = Logger.Level.BASIC;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return feignLoggerLevel;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create()
                                .setMaxConnTotal(maxConnTotal)
                                .setMaxConnPerRoute(maxConnPerRoute)
                                .disableCookieManagement()
                                .useSystemProperties() // Do we need to use it ?
                                .build();
    }
}
