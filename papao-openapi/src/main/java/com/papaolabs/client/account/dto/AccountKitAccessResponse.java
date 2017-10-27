package com.papaolabs.client.account.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AccountKitAccessResponse {
    private long id;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_refresh_interval")
    private long tokenRefreshInterval;
}
