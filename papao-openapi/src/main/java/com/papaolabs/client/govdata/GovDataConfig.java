package com.papaolabs.client.govdata;

import feign.Feign;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.gson.GsonEncoder;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static feign.FeignException.errorStatus;
import static org.apache.commons.lang.CharEncoding.UTF_8;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class GovDataConfig {
    @Value("${seoul.api.animal.url}")
    private String animalApiUrl;

    @Bean
    public GovDataClient animalApiClient() {
        return Feign.builder()
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder())
                    .decoder(new JAXBDecoder(new JAXBContextFactory.Builder()
                            .withMarshallerJAXBEncoding(UTF_8)
                            .build()))
                    .target(GovDataClient.class, animalApiUrl);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            private ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

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
