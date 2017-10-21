package com.papaolabs.api.infrastructure.persistence.restapi.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteAccountResponseResponse {

	@JsonProperty("success")
	private boolean removed;
}
