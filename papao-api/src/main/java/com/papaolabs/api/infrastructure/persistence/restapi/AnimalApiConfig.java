package com.papaolabs.api.infrastructure.persistence.restapi;

import com.papaolabs.api.infrastructure.persistence.restapi.feign.AnimalApiClient;
import feign.Feign;
import feign.gson.GsonEncoder;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.apache.commons.lang.CharEncoding.UTF_8;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AnimalApiConfig {
    @Value("${seoul.api.animal.url}")
    private String animalApiUrl;

    @Bean
    public AnimalApiClient animalApiClient() {
        return Feign.builder()
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder())
                    .decoder(new JAXBDecoder(new JAXBContextFactory.Builder()
                            .withMarshallerJAXBEncoding(UTF_8)
                            .build()))
                    .target(AnimalApiClient.class, animalApiUrl);
    }
}
