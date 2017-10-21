package com.papaolabs.api.domain.service;

import com.papaolabs.api.infrastructure.persistence.restapi.account.AccountApiClient;
import com.papaolabs.api.infrastructure.persistence.restapi.account.dto.AccountKitAccessResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.account.dto.AccountKitProfileResponse;
import com.papaolabs.api.infrastructure.persistence.restapi.account.dto.DeleteAccountResponseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class AccountKitService {
    @NotNull
    private final AccountApiClient accountApiClient;
    @Value("${spring.social.facebook.appId}")
    private String appId;
    @Value("${spring.social.facebook.appSecret}")
    private String appSecret;

    public AccountKitService(AccountApiClient accountApiClient) {
        this.accountApiClient = accountApiClient;
    }

    public AccountKitAccessResponse validateAuthorizationCode(String code) throws IOException {
        final String accessToken = "AA|" + appId + "|" + appSecret;
        return accountApiClient.getAccessToken(code, accessToken);
    }

    public AccountKitProfileResponse getProfile(String accessToken) throws IOException {
        String hash = null;
        try {
            hash = hashMac(accessToken, appSecret);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        AccountKitProfileResponse accountKitProfileResponse = accountApiClient.validateAccessToken(accessToken, hash);
        if (isEmpty(accountKitProfileResponse.getEmail())) {
            accountKitProfileResponse.setEmail("-1");
        }
        if (isEmpty(accountKitProfileResponse.getPhone())) {
            AccountKitProfileResponse.AccountKitPhone phone = new AccountKitProfileResponse.AccountKitPhone();
            phone.setNumber("-1");
            accountKitProfileResponse.setPhone(phone);
        }
        return accountKitProfileResponse;
    }

    public DeleteAccountResponseResponse removeAccount(String accountId) throws IOException {
        final String accessToken = "AA|" + appId + "|" + appSecret;
        return accountApiClient.deleteAccount(accountId, accessToken);
    }

    public static String hashMac(String text, String secretKey)
            throws SignatureException {
        try {
            Key sk = new SecretKeySpec(secretKey.getBytes(), HASH_ALGORITHM);
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            mac.init(sk);
            final byte[] hmac = mac.doFinal(text.getBytes());
            return toHexString(hmac);
        } catch (NoSuchAlgorithmException e1) {// throw an exception or pick a different encryption method
            throw new SignatureException(
                    "error building signature, no such algorithm in device "
                            + HASH_ALGORITHM);
        } catch (InvalidKeyException e) {
            throw new SignatureException(
                    "error building signature, invalid key " + HASH_ALGORITHM);
        }
    }

    private static final String HASH_ALGORITHM = "HmacSHA256";

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return sb.toString();
    }
}
