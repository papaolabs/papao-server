package com.papaolabs.api.infrastructure.persistence.restapi.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountKitProfileResponse {

    private long id;

    @JsonProperty("phone")
    private AccountKitPhone phone;
    private String email;

    private Application application;

    @Data
    public static class Application {
        private String id;
    }

    @Data
    public static class AccountKitPhone {
        @JsonProperty("number")
        private String mdn;

        @JsonProperty("country_prefix")
        private String countryCode;

        @JsonProperty("national_number")
        private String number;
    }
}
