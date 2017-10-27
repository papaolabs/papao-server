package com.papaolabs.client.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountKitProfileResponse {
    private long id;
    private AccountKitPhone phone;
    private AccountKitEmail email;
    private AccountKitApplication application;

    @Data
    public static class AccountKitApplication {
        private String id;
    }

    @Data
    public static class AccountKitEmail {
        private String address;
    }

    @Data
    public static class AccountKitPhone {
        @SerializedName("national_number")
        private String nationalNumber;
        @SerializedName("country_prefix")
        private String countryCode;
        private String number;
    }
}
