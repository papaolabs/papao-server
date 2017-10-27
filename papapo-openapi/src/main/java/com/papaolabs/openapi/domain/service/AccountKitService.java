package com.papaolabs.openapi.domain.service;

import com.papaolabs.openapi.infrastructure.persistence.feign.account.dto.AccountKitAccessResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.account.dto.AccountKitProfileResponse;
import com.papaolabs.openapi.infrastructure.persistence.feign.account.dto.DeleteAccountResponseResponse;

public interface AccountKitService {
    AccountKitAccessResponse validateAuthorizationCode(String code);

    AccountKitProfileResponse getProfile(String accessToken);

    DeleteAccountResponseResponse removeAccount(String accountId);
}
