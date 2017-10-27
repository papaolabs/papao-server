package com.papaolabs.client.account;

import com.papaolabs.client.LoggingFallbackFactory;
import com.papaolabs.client.account.dto.AccountKitAccessResponse;
import com.papaolabs.client.account.dto.AccountKitProfileResponse;
import com.papaolabs.client.account.dto.DeleteAccountResponseResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountApiClientFallbackFactory implements LoggingFallbackFactory<AccountApiClient> {
    private static final AccountApiClient FALLBACK = new AccountApiClientFallbackFactory.GraphApiFallback();

    @Override
    public AccountApiClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class GraphApiFallback implements AccountApiClient {
        @Override
        public AccountKitAccessResponse getAccessToken(String code, String accessToken) {
            return null;
        }

        @Override
        public AccountKitProfileResponse validateAccessToken(String accessToken, String appSecretRoot) {
            return null;
        }

        @Override
        public void logout(String accessToken) {
        }

        @Override
        public void invalidateAllTokens(String accountId, String appSecret) {
        }

        @Override
        public DeleteAccountResponseResponse deleteAccount(String accountId, String appSecret) {
            return null;
        }
    }
}
