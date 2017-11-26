package com.papaolabs.client.account;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AccountKitApiConfig {
    @Value("${facebook.application.apiUrl}")
    private String host;
    @Value("${facebook.application.apiVersion}")
    private String version;

    @Bean
    public AccountApiClient accountApiClient() {
        return Feign.builder()
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logLevel(Logger.Level.FULL)
                    .target(AccountApiClient.class, host + version);
    }
}
