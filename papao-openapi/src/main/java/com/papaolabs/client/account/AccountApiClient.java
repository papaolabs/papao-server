package com.papaolabs.client.account;

import com.papaolabs.client.account.dto.AccountKitAccessResponse;
import com.papaolabs.client.account.dto.AccountKitProfileResponse;
import com.papaolabs.client.account.dto.DeleteAccountResponseResponse;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "accountApiClient", fallbackFactory = AccountApiClientFallbackFactory.class)
public interface AccountApiClient {
    @RequestLine("GET /access_token?grant_type=authorization_code&code={code}&access_token={access_token}")
    AccountKitAccessResponse getAccessToken(@Param("code") String code, @Param("access_token") String accessToken);

    @RequestLine("GET /me/?access_token={access_token}&appsecret_proof={appsecret_proof}")
    AccountKitProfileResponse validateAccessToken(@Param("access_token") String accessToken,
                                                  @Param("appsecret_proof") String appSecretRoot);

    @RequestLine("GET /logout?access_token={access_token}")
    void logout(@Param("access_token") String accessToken);

    @RequestLine("GET /{account_id}/invalidate_all_tokens?access_token={access_token}")
    void invalidateAllTokens(@Param("account_id") String accountId, @Param("access_token") String appSecret);

    @RequestLine("POST /{account_id}?access_token={access_token}")
    DeleteAccountResponseResponse deleteAccount(@Param("account_id") String accountId,
                                                @Param("access_token") String appSecret);
}
