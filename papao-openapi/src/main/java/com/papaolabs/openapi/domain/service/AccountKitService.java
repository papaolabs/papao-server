package com.papaolabs.openapi.domain.service;

import com.papaolabs.client.account.dto.AccountKitAccessResponse;
import com.papaolabs.client.account.dto.AccountKitProfileResponse;
import com.papaolabs.client.account.dto.DeleteAccountResponseResponse;

public interface AccountKitService {
    AccountKitAccessResponse validateAuthorizationCode(String code);

    AccountKitProfileResponse getProfile(String accessToken);

    DeleteAccountResponseResponse removeAccount(String accountId);
}
