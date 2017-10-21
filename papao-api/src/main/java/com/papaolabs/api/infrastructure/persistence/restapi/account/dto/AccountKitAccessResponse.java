package com.papaolabs.api.infrastructure.persistence.restapi.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
