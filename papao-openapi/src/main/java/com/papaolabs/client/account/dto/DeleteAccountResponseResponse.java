package com.papaolabs.client.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteAccountResponseResponse {
    @JsonProperty("success")
    private boolean removed;
}
